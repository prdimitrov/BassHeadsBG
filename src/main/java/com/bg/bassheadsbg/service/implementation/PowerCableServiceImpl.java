package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddPowerCableDTO;
import com.bg.bassheadsbg.model.dto.details.PowerCableDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.PowerCableSummaryDTO;
import com.bg.bassheadsbg.model.entity.cables.PowerCable;
import com.bg.bassheadsbg.model.entity.images.PowerCableImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.PowerCableDetailsHelperDTO;
import com.bg.bassheadsbg.repository.PowerCableImageRepository;
import com.bg.bassheadsbg.repository.PowerCableRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.PowerCableService;
import com.bg.bassheadsbg.util.ObjectLogger;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PowerCableServiceImpl implements PowerCableService {

    private final PowerCableRepository powerCableRepository;
    private final PowerCableImageRepository powerCableImageRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final ExRateService exRateService;

    public PowerCableServiceImpl(PowerCableRepository powerCableRepository, PowerCableImageRepository powerCableImageRepository, ModelMapper modelMapper, UserRepository userRepository, MessageSource messageSource, ExRateService exRateService) {
        this.powerCableRepository = powerCableRepository;
        this.powerCableImageRepository = powerCableImageRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.exRateService = exRateService;
    }

    @Override
    public AddPowerCableDTO createNewCableDTO() {
        return new AddPowerCableDTO();
    }

    @Transactional
    @Override
    public long addCable(AddPowerCableDTO addPowerCableDTO) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        PowerCable powerCable = modelMapper.map(addPowerCableDTO, PowerCable.class);
        checkEntityExists(powerCable.getBrand(), powerCable.getModel());

        PowerCable savedPowerCable = powerCableRepository.save(powerCable);

        updateCableImages(user, powerCable, addPowerCableDTO);

        ObjectLogger.logMessage(user,
                "added",
                powerCable,
                powerCable.getId(),
                powerCable.getBrand(),
                powerCable.getModel());

        return savedPowerCable.getId();
    }

    @Transactional
    @Override
    public long editCable(AddPowerCableDTO addPowerCableDTO, List<MultipartFile> multipartFiles) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        PowerCable entity = powerCableRepository.findById(addPowerCableDTO.getId())
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, addPowerCableDTO.getId()));
        if (addPowerCableDTO.getImageFiles() != null) {
            entity.getImageFiles().clear();
            updateCableImages(user, entity, addPowerCableDTO);
        }

        entity = modelMapper.map(addPowerCableDTO, PowerCable.class);

        PowerCable savedPowerCable = powerCableRepository.saveAndFlush(entity);

        ObjectLogger.logMessage(user,
                "edited",
                savedPowerCable,
                savedPowerCable.getId(),
                savedPowerCable.getBrand(),
                savedPowerCable.getModel());

        return savedPowerCable.getId();
    }

    @Override
    public void deleteCable(long cableId) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Optional<PowerCable> optCable = powerCableRepository.findById(cableId);

        if (optCable.isPresent()) {
            PowerCable powerCable = optCable.get();
            powerCableRepository.deleteById(cableId);
            ObjectLogger.logDeleteMessage(user,
                    powerCable,
                    powerCable.getBrand(),
                    powerCable.getModel());
        }
    }

    @Override
    @Transactional
    public List<PowerCableSummaryDTO> getAllPowerCablesSummarySorted() {
        return powerCableRepository.findAllPowerCablesUserLikesCountOrderByBrandAndModel()
                .stream()
                .map(this::mapPowerCableToPowerCableSummaryDTO)
                .toList();
    }


    @Override
    @Transactional
    public PowerCableDetailsDTO getCableDetails(Long id) {
        PowerCable powerCable = powerCableRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Cable with id " + id + " not found!", id));

        Hibernate.initialize(powerCable.getImageFiles());

        PowerCableDetailsDTO powerCableDetailsDTO = modelMapper.map(powerCable, PowerCableDetailsDTO.class);

        powerCableDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        powerCableDetailsDTO.setImageFiles(powerCable.getImageFiles()
                .stream().map(image -> Base64
                        .getEncoder().encodeToString(image.getImageData()))
                .collect(Collectors.toList()));

        return powerCableDetailsDTO;
    }

    @Override
    @Transactional
    public PowerCableDetailsHelperDTO getPowerCableDetailsHelper(Long id) {
        PowerCableDetailsDTO powerCableDetails = getCableDetails(id);

        return new PowerCableDetailsHelperDTO(powerCableDetails);
    }

    @Override
    public boolean likeCable(Long id) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        PowerCable powerCable = powerCableRepository.findPowerCableByUserLikes(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        boolean alreadyLiked = powerCable.getUserLikes()
                .stream()
                .anyMatch(userLike -> userLike.getUsername().equals(user.getUsername()));

        if (alreadyLiked) {
            return false;
        }

        powerCable.getUserLikes().add(user);
        powerCableRepository.save(powerCable);

        ObjectLogger.logMessage(user,
                "liked",
                powerCable,
                id,
                powerCable.getBrand(),
                powerCable.getModel());

        return true;
    }

    private void checkEntityExists(String brand, String model) {
        Optional<PowerCable> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<PowerCable> findByBrandAndModel(String brand, String model) {
        return powerCableRepository.findByBrandAndModel(brand, model);
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void updateCableImages(UserEntity user, PowerCable powerCable, AddPowerCableDTO addPowerCableDTO) throws IOException {
        if (addPowerCableDTO.getImageFiles() != null && !addPowerCableDTO.getImageFiles().isEmpty()) {

            powerCableImageRepository.deleteByPowerCable(powerCable);

            List<PowerCableImage> cableImages = new ArrayList<>();

            List<MultipartFile> imageFiles = addPowerCableDTO.getImageFiles();
            for (int i = 0; i < imageFiles.size(); i++) {
                MultipartFile file = imageFiles.get(i);
                if (!file.isEmpty()) {
                    PowerCableImage powerCableImage = new PowerCableImage();
                    powerCableImage.setImageData(file.getBytes());
                    powerCableImage.setPowerCable(powerCable);
                    cableImages.add(powerCableImage);
                }
            }
            powerCableImageRepository.saveAll(cableImages);
        } else {
            ObjectLogger.logMessageWithoutImages(user,
                    "updated",
                    powerCable,
                    powerCable.getId(),
                    powerCable.getBrand(),
                    powerCable.getModel());
        }
    }

    private PowerCableSummaryDTO mapPowerCableToPowerCableSummaryDTO(PowerCable powerCable) {
        PowerCableSummaryDTO powerCableSummaryDTO = modelMapper.map(powerCable, PowerCableSummaryDTO.class);
        powerCableSummaryDTO.setLikes(powerCable.getLikes());
        byte[] image = powerCable.getImageFiles().get(0).getImageData();
        powerCableSummaryDTO.setImageFile(Base64.getEncoder().encodeToString(image));
        return powerCableSummaryDTO;
    }

    private static UserDetails getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails;
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }
}
