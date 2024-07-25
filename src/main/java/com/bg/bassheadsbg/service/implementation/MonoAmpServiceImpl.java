package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.*;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MonoAmpServiceImpl implements MonoAmpService {

    private final MonoAmplifierRepository repository;
    private final ModelMapper modelMapper;
    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public MonoAmpServiceImpl(MonoAmplifierRepository repository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddMonoAmpDTO createNewAddMonoAmpDTO() {
        return new AddMonoAmpDTO();
    }

    @Override
    public long addDevice(AddMonoAmpDTO addDeviceDTO) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            checkEntityExists(getBrand(addDeviceDTO), getModel(addDeviceDTO));
            MonoAmplifier entity = mapToDevice(addDeviceDTO);
            long deviceId = repository.save(entity).getId();

            log.info("User with id ({}) and username ({}) added device with ID ({}), brand ({}), and model ({}).",
                    user.getId(), user.getUsername(), deviceId, getBrand(addDeviceDTO), getModel(addDeviceDTO));

            return deviceId;
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public long editDevice(AddMonoAmpDTO addDeviceDTO) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            MonoAmplifier entity = mapEditedDevice(addDeviceDTO);
            long deviceId = repository.save(entity).getId();

            log.info("User with id ({}) and username ({}) edited device with ID ({}), brand ({}), and model ({}).",
                    user.getId(), user.getUsername(), deviceId, getBrand(addDeviceDTO), getModel(addDeviceDTO));

            return deviceId;
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public void deleteDevice(long deviceId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            Optional<MonoAmplifier> deviceOptional = repository.findById(deviceId);

            if (deviceOptional.isPresent()) {
                MonoAmplifier device = deviceOptional.get();
                repository.deleteById(deviceId);

                log.info("User with id ({}) and username ({}) deleted device with ID ({}), brand ({}), and model ({}).",
                        user.getId(), user.getUsername(), deviceId, device.getBrand(), device.getModel());
            }
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public MonoAmpDetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new DeviceNotFoundException("Device with id " + id + " not found!", id));
    }

    @Override
    public MonoAmpDetailsHelperDTO getDeviceDetailsHelper(Long id) {
        MonoAmpDetailsDTO deviceDetails = getDeviceDetails(id);
        return new MonoAmpDetailsHelperDTO(deviceDetails);
    }

    @Override
    public List<MonoAmpSummaryDTO> getAllDeviceSummary() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(MonoAmplifier::getLikes)
                        .reversed()
                        .thenComparing(a -> a.getBrand().toLowerCase())
                        .thenComparing(a -> a.getModel().toLowerCase()))
                .map(this::toSummaryDTO)
                .toList();
    }

    @Override
    public void likeDevice(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        UserEntity user;
        if (principal instanceof UserDetails userDetails) {
            user = getUserEntity(userDetails.getUsername());
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }

        Optional<MonoAmplifier> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            MonoAmplifier entity = optionalEntity.get();
            addLikeToEntity(entity, user);
            repository.save(entity);
        } else {
            throw new DeviceNotFoundException("Device with id " + id + " not found!", id);
        }
    }



    @Override
    public void updateDeviceImageUrls(String oldUrl, String newUrl) {
        List<MonoAmplifier> monoAmplifiers = repository.findByImagesContaining(oldUrl);

        for (MonoAmplifier monoAmplifier : monoAmplifiers) {
            List<String> images = monoAmplifier.getImages();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).equals(oldUrl)) {
                    images.set(i, newUrl);
                }
            }
            monoAmplifier.setImages(images);
            repository.save(monoAmplifier);
        }
    }

    private MonoAmplifier mapToDevice(AddMonoAmpDTO addDeviceDTO) throws JsonProcessingException {
        createImageListDetailsDTO(addDeviceDTO);
        return modelMapper.map(addDeviceDTO, MonoAmplifier.class);
    }

    private MonoAmplifier mapEditedDevice(AddMonoAmpDTO addMonoAmpDTO) throws JsonProcessingException {
        createImageListDetailsDTO(addMonoAmpDTO);
        return modelMapper.map(addMonoAmpDTO, MonoAmplifier.class);
    }

    private void createImageListDetailsDTO(AddMonoAmpDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("mono_amplifier_images");
        imageProducer.sendMessage(imageListDetailsDTO);
    }

    private MonoAmpDetailsDTO toDetailsDTO(MonoAmplifier monoAmplifier) {
        MonoAmpDetailsDTO monoAmpDetailsDTO = modelMapper.map(monoAmplifier, MonoAmpDetailsDTO.class);
        monoAmpDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return monoAmpDetailsDTO;
    }

    private MonoAmpSummaryDTO toSummaryDTO(MonoAmplifier monoAmplifier) {
        MonoAmpSummaryDTO monoAmpSummaryDTO = modelMapper.map(monoAmplifier, MonoAmpSummaryDTO.class);
        monoAmpSummaryDTO.setLikes(monoAmplifier.getLikes());
        return monoAmpSummaryDTO;
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void addLikeToEntity(MonoAmplifier entity, UserEntity user) {
        List<UserEntity> userLikes = entity.getUserLikes();
        long userId = user.getId();

        for (UserEntity existingUser : userLikes) {
            if (existingUser.getId() == userId) {
                String errorMessage = messageSource.getMessage(
                        ExceptionMessages.DEVICE_ALREADY_LIKED,
                        null,
                        LocaleContextHolder.getLocale()
                );
                throw new DeviceAlreadyLikedException(errorMessage);
            }
        }

        userLikes.add(user);
    }

    private void checkEntityExists(String brand, String model) {
        Optional<MonoAmplifier> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale()
            );
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<MonoAmplifier> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    private String getBrand(AddMonoAmpDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    private String getModel(AddMonoAmpDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }
}