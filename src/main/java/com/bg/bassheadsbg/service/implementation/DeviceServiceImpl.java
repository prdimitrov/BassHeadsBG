package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.interfaces.AddDeviceDTO;
import com.bg.bassheadsbg.model.interfaces.DetailsDeviceDTO;
import com.bg.bassheadsbg.model.interfaces.DeviceEntity;
import com.bg.bassheadsbg.model.interfaces.DeviceImageEntity;
import com.bg.bassheadsbg.model.interfaces.DeviceSummaryDTO;
import com.bg.bassheadsbg.repository.DeviceImageRepository;
import com.bg.bassheadsbg.repository.DeviceRepository;
import com.bg.bassheadsbg.service.interfaces.DeviceService;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import com.bg.bassheadsbg.util.ObjectLogger;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Service implementation for managing devices.
 * This class provides methods for creating, editing, deleting, and retrieving devices,
 * also handling the likes and updating images.
 */
@RequiredArgsConstructor
public abstract class DeviceServiceImpl<
        AddDTO extends AddDeviceDTO,
        DetailsDTO extends DetailsDeviceDTO,
        SummaryDTO extends DeviceSummaryDTO,
        HelperDTO,
        Image extends DeviceImageEntity<Device>,
        Device extends DeviceEntity<Image>>
        implements DeviceService<AddDTO, DetailsDTO, SummaryDTO, HelperDTO> {
    private static final String ADDED = "added";
    private static final String LIKED = "liked";
    private static final String UPDATED = "updated";
    private static final int ZERO = 0;
    protected final DeviceRepository<Device> deviceRepository;
    protected final DeviceImageRepository<Image, Device> deviceImageRepository;
    protected final ModelMapper modelMapper;
    protected final ExRateService exRateService;
    protected final UserService userService;
    protected final MessageSource messageSource;

    private final Supplier<AddDTO> addDtoSupplier;
    private final Class<Device> deviceClass;
    private final Class<DetailsDTO> detailsClass;
    private final Class<SummaryDTO> summaryClass;
    private final Function<DetailsDTO, HelperDTO> helperFactory;
    private final Supplier<Image> imageFactory;

    @Override
    public AddDTO createNewDevice() {
        return addDtoSupplier.get();
    }

    @Override
    @Transactional
    public long addDevice(final AddDTO addDTO) throws IOException {
        UserEntity user = getUserEntity(getUserDetails());
        String brand = addDTO.getBrand().trim();
        String model = addDTO.getModel().trim();
        assertUniqueForAddDevice(brand, model);
        Device device = modelMapper.map(addDTO, deviceClass);
        device = deviceRepository.save(device);
        updateDeviceImages(user, device, addDTO);
        long deviceId = device.getId();
        ObjectLogger.logMessage(user, ADDED, device, deviceId, brand, model);
        return deviceId;
    }

    @Override
    @Transactional
    public long editDevice(final AddDTO addDTO) throws IOException {
        UserEntity user = getUserEntity(getUserDetails());
        Device existing = deviceRepository.findById(addDTO.getId())
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, addDTO.getId()));
        String brand = addDTO.getBrand().trim();
        String model = addDTO.getModel().trim();
        assertUniqueForEditDevice(existing.getId(), brand, model);
        modelMapper.map(addDTO, existing);
        if (addDTO.getImageFiles() != null) {
            existing.getImageFiles().clear();
            updateDeviceImages(user, existing, addDTO);
        }
        Device saved = deviceRepository.saveAndFlush(existing);
        ObjectLogger.logMessage(user, "edited", saved, saved.getId(), brand, model);
        return saved.getId();
    }

    @Override
    public void deleteDevice(final long id) {
        UserEntity user = getUserEntity(getUserDetails());
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));
        deviceRepository.delete(device);
        ObjectLogger.logDeleteMessage(user, device, device.getBrand(), device.getModel());
    }

    @Override
    @Transactional
    public List<SummaryDTO> getAllDeviceSummarySorted() {
        return deviceRepository.findAllDevicesWithUserLikesCountOrderByBrandAndModel()
                .stream()
                .map(this::mapDeviceToDeviceSummaryDTO)
                .toList();
    }

    @Override
    @Transactional
    public DetailsDTO getDeviceDetails(final Long id) throws DeviceNotFoundException {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));
        Hibernate.initialize(device.getImageFiles());
        DetailsDTO detailsDTO = modelMapper.map(device, detailsClass);
        detailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        detailsDTO.setImageFiles(device.getImageFiles()
                .stream()
                .map(image -> Base64.getEncoder().encodeToString(image.getImageData())).toList());
        return detailsDTO;
    }

    @Override
    @Transactional
    public HelperDTO getDeviceDetailsHelper(final Long id) {
        return helperFactory.apply(getDeviceDetails(id));
    }

    @Override
    @Transactional
    public boolean likeDevice(final Long id) {
        UserEntity user = getUserEntity(getUserDetails());
        Device device = deviceRepository.findDeviceByUserLikes(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));
        if (device.getUserLikes().stream().anyMatch(userLike ->
                userLike.getUsername().equals(user.getUsername()))) return false;
        device.getUserLikes().add(user);
        deviceRepository.save(device);
        ObjectLogger.logMessage(user, LIKED, device, id, device.getBrand(), device.getModel());
        return true;
    }

    private UserEntity getUserEntity(final String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    /**
     * This static method returns username of the UserDetails, by using the Authentication interface
     * provided by spring security core.
     *
     * @return userDetails, if the user is authenticated
     * @throws UserNotAuthenticatedException, if the user is not authenticated.
     */
    private static String getUserDetails() throws UserNotAuthenticatedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    private Optional<Device> findByBrandAndModel(final String brand, final String model) {
        return deviceRepository.findByBrandAndModel(brand, model);
    }

    /**
     * This method is used to update the images of devices.
     *
     * @param user   is used to pass user data to ObjectLogger class, that is used
     *               to log messages in the console.
     * @param device the device, whose images will be set.
     * @param addDTO the addDTO, whose images will be mapped to the device.
     * @throws IOException if an error occurs while processing the images.
     */
    private void updateDeviceImages(final UserEntity user, final Device device, final AddDTO addDTO) throws IOException {
        List<MultipartFile> files = addDTO.getImageFiles();
        if (files != null && !files.isEmpty()) {
            deviceImageRepository.deleteByDevice(device);
            List<Image> images = new ArrayList<>();
            for (MultipartFile f : files) {
                if (!f.isEmpty()) {
                    Image img = imageFactory.get();
                    img.setImageData(f.getBytes());
                    img.setDevice(device);
                    images.add(img);
                }
            }
            deviceImageRepository.saveAll(images);
        } else {
            ObjectLogger.logMessageWithoutImages(user, UPDATED, device, device.getId(), device.getBrand(), device.getModel());
        }
    }

    private void assertUniqueForAddDevice(final String brand, final String model) {
        if (deviceRepository.findByBrandAndModel(brand, model).isPresent()) {
            String msg = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS, null, LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(msg);
        }
    }

    private void assertUniqueForEditDevice(final long id, final String brand, final String model) {
        if (deviceRepository.findOtherByBrandAndModel(brand, model, id).isPresent()) {
            String msg = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS, null, LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(msg);
        }
    }

    /**
     * This method is used to map Device to SummaryDTO.
     *
     * @param device is used as a method parameter, that will be converted to a SummaryDTO.
     * @return SummaryDTO with the needed image and user likes set properly.
     */
    private SummaryDTO mapDeviceToDeviceSummaryDTO(final Device device) {
        SummaryDTO summaryDTO = modelMapper.map(device, summaryClass);
        summaryDTO.setLikes(device.getLikes());
        summaryDTO.setImageFile(Base64.getEncoder().encodeToString(device.getImageFiles().get(ZERO).getImageData()));
        return summaryDTO;
    }
}