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
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Service implementation for managing devices.
 * This class provides methods for creating, editing, deleting, and retrieving devices,
 * also handling the likes and updating images.
 */
@RequiredArgsConstructor
public abstract class DeviceServiceImpl<AddDTO extends AddDeviceDTO,
        DetailsDTO extends DetailsDeviceDTO,
        SummaryDTO extends DeviceSummaryDTO,
        HelperDTO,
        Image extends DeviceImageEntity<Device>,
        Device extends DeviceEntity<Image>>
        implements DeviceService<AddDTO, DetailsDTO, SummaryDTO, HelperDTO> {
    private static final int ZERO = 0;
    private static final String DEVICE_ALREADY_EXISTS = ExceptionMessages.DEVICE_ALREADY_EXISTS;
    private static final String DEVICE_NOT_FOUND = ExceptionMessages.DEVICE_NOT_FOUND;
    private static final String USER_NOT_FOUND = ExceptionMessages.USER_NOT_FOUND;
    private static final String USER_NOT_AUTH = ExceptionMessages.USER_NOT_AUTH;
    private static final String DELETED = "deleted";
    private static final String EDITED = "edited";
    private static final String ADDED = "added";
    private static final String LIKED = "liked";
    private final DeviceRepository<Device> deviceRepository;
    private final ModelMapper modelMapper;
    private final ExRateService exRateService;
    private final UserService userService;
    private final MessageSource messageSource;
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
        updateDeviceImages(device, addDTO.getImageFiles());
        device = deviceRepository.save(device);
        long deviceId = device.getId();
        logMessage(user, ADDED, device, deviceId, brand, model);
        return deviceId;
    }

    @Override
    @Transactional
    public long editDevice(final AddDTO addDTO) throws IOException {
        UserEntity user = getUserEntity(getUserDetails());
        long id = addDTO.getId();
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(DEVICE_NOT_FOUND, id));
        String brand = addDTO.getBrand().trim();
        String model = addDTO.getModel().trim();
        assertUniqueForEditDevice(existingDevice.getId(), brand, model);
        modelMapper.map(addDTO, existingDevice);
        if (addDTO.getImageFiles() != null) {
            updateDeviceImages(existingDevice, addDTO.getImageFiles());
        }
        Device saved = deviceRepository.saveAndFlush(existingDevice);
        logMessage(user, EDITED, saved, id, brand, model);
        return id;
    }

    @Override
    public void deleteDevice(final long id) {
        UserEntity user = getUserEntity(getUserDetails());
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(DEVICE_NOT_FOUND, id));
        deviceRepository.delete(device);
        logMessage(user, DELETED, device, id, device.getBrand(), device.getModel());
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
                .orElseThrow(() -> new DeviceNotFoundException(DEVICE_NOT_FOUND, id));
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
                .orElseThrow(() -> new DeviceNotFoundException(DEVICE_NOT_FOUND, id));
        if (device.getUserLikes().stream().anyMatch(userLike -> userLike.equals(user))) return false;
        device.getUserLikes().add(user);
        deviceRepository.save(device);
        logMessage(user, LIKED, device, id, device.getBrand(), device.getModel());
        return true;
    }

    private UserEntity getUserEntity(final String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
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
            throw new UserNotAuthenticatedException(USER_NOT_AUTH);
        }
    }

    /**
     * This method is used to add or update the images of devices.
     *
     * @param device the device, whose images will be set.
     * @param files  the addDTO.getImageFiles(), whose images will be mapped to the device.
     * @throws IOException if an error occurs while processing the images.
     */

    private void updateDeviceImages(final Device device, final List<MultipartFile> files) throws IOException {
        device.getImageFiles().clear();
        if (files == null || files.isEmpty()) return;
        for (MultipartFile f : files) {
            if (f.isEmpty()) continue;
            Image img = imageFactory.get();
            img.setImageData(f.getBytes());
            img.setDevice(device);
            device.getImageFiles().add(img);
        }
    }

    private void assertUniqueForAddDevice(final String brand, final String model) {
        if (deviceRepository.findByBrandAndModel(brand, model).isPresent()) {
            throw new DeviceAlreadyExistsException(
                    messageSource.getMessage(DEVICE_ALREADY_EXISTS, null, LocaleContextHolder.getLocale()));
        }
    }

    private void assertUniqueForEditDevice(final long id, final String brand, final String model) {
        if (deviceRepository.findOtherByBrandAndModel(brand, model, id).isPresent()) {
            throw new DeviceAlreadyExistsException(
                    messageSource.getMessage(DEVICE_ALREADY_EXISTS, null, LocaleContextHolder.getLocale()));
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

    private void logMessage(UserEntity user, String message, Device device, long deviceId,
                            String brand, String model) {
        ObjectLogger.logMessage(user, message, device, deviceId, brand, model);
    }
}