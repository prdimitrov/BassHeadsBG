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
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    public long addCable(AddPowerCableDTO addPowerCableDTO, List<MultipartFile> multipartFiles) throws IOException {
        Object principal = getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());

            PowerCable powerCable = modelMapper.map(addPowerCableDTO, PowerCable.class);
            checkEntityExists(powerCable.getBrand(), powerCable.getModel());

            PowerCable savedPowerCable = powerCableRepository.save(powerCable);

            List<PowerCableImage> powerCableImages = new ArrayList<>();
            for (MultipartFile file : multipartFiles) {
                if (!file.isEmpty()) {
                    PowerCableImage powerCableImage = new PowerCableImage();
                    powerCableImage.setImageData(file.getBytes());
                    powerCableImage.setPowerCable(savedPowerCable);
                    powerCableImages.add(powerCableImage);
                }
            }

            powerCableImageRepository.saveAll(powerCableImages);

            log.info("User with id ({}) and username ({}) added cable with ID ({}), brand ({}), and model ({}).",
                    user.getId(), user.getUsername(), savedPowerCable.getId(),
                    savedPowerCable.getBrand(), savedPowerCable.getModel());

            return savedPowerCable.getId();
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Transactional
    @Override
    public long editCable(AddPowerCableDTO addPowerCableDTO, List<MultipartFile> multipartFiles) throws IOException {
        Object principal = getPrincipal();

        if (!(principal instanceof UserDetails userDetails)) {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }

        UserEntity user = getUserEntity(userDetails.getUsername());
        PowerCable entity = modelMapper.map(addPowerCableDTO, PowerCable.class);

        // Process images only, if new valid ones are provided!!
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            // Filter out empty files or files with no filename!!
            List<MultipartFile> validFiles = multipartFiles.stream()
                    .filter(file -> !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
                    .toList();

            if (!validFiles.isEmpty()) {
                Optional<PowerCable> existingCable = getCable(entity.getId());
                existingCable.ifPresent(powerCableImageRepository::deleteByPowerCable);

                // Save new valid images
                List<PowerCableImage> powerCableImages = new ArrayList<>();
                for (MultipartFile file : validFiles) {
                    PowerCableImage powerCableImage = new PowerCableImage();
                    powerCableImage.setImageData(file.getBytes());
                    powerCableImage.setPowerCable(entity);
                    powerCableImages.add(powerCableImage);
                }
                powerCableImageRepository.saveAll(powerCableImages);
            }
        }

        PowerCable savedPowerCable = powerCableRepository.save(entity);

        log.info("User with id ({}) and username ({}) edited device with ID ({}), brand ({}) and model ({})",
                user.getId(), user.getUsername(), entity.getId(),
                entity.getBrand(), entity.getModel());

        return savedPowerCable.getId();
    }


    public List<byte[]> getCableImages(Long cableId) {
        Optional<PowerCable> optCable = getCable(cableId);

        if (optCable.isPresent()) {
            PowerCable powerCable = optCable.get();
            // Extract the byte[] data from the CableImage entities
            return powerCable.getImageFiles().stream()
                    .map(PowerCableImage::getImageData)  // Get the byte[] from each CableImage entity
                    .toList();
        }

        return null;  // Return null if cable is not found
    }

    @Override
    public Optional<PowerCable> getCable(Long id) {
        return powerCableRepository.findById(id);
    }

    @Override
    public void deleteCable(long cableId) {
        Object principal = getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            Optional<PowerCable> optCable = powerCableRepository.findById(cableId);

            if (optCable.isPresent()) {
                PowerCable powerCable = optCable.get();
                powerCableRepository.deleteById(cableId);
                log.info("User with id ({}) and username ({}) deleted cable with ID ({}), brand ({}) and model ({})",
                        user.getId(),
                        user.getUsername(),
                        cableId,
                        powerCable.getBrand(),
                        powerCable.getModel());
            }
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public List<PowerCableSummaryDTO> getAllCableSummary() {
        return powerCableRepository.findAll()
                .stream()
                .sorted()
                .map(this::toSummaryDTO)
                .toList();
    }

    @Override
    @Transactional
    public PowerCableDetailsDTO getCableDetails(Long id) {
        PowerCable powerCable = powerCableRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Cable with id " + id + " not found!", id));

        Hibernate.initialize(powerCable.getImageFiles());

        return toDetailsDTO(powerCable);
    }

    @Override
    @Transactional
    public PowerCableDetailsHelperDTO getPowerCableDetailsHelper(Long id) {
        PowerCableDetailsDTO powerCableDetails = getCableDetails(id);

        return new PowerCableDetailsHelperDTO(powerCableDetails);
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
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

    private static Object getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }

    private PowerCableSummaryDTO toSummaryDTO(PowerCable powerCable) {
        PowerCableSummaryDTO powerCableSummaryDTO = modelMapper.map(powerCable, PowerCableSummaryDTO.class);
        return powerCableSummaryDTO;
    }

    private PowerCableDetailsDTO toDetailsDTO(PowerCable powerCable) {
        PowerCableDetailsDTO powerCableDetailsDTO = modelMapper.map(powerCable, PowerCableDetailsDTO.class);

        powerCableDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        powerCableDetailsDTO.setImageFiles(powerCable.getImageFiles()
                .stream().map(image -> Base64
                        .getEncoder().encodeToString(image.getImageData()))
                .collect(Collectors.toList()));

        return powerCableDetailsDTO;
    }
}
