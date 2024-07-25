package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.*;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MultiChannelAmpDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MultiChannelAmplifierRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MultiChannelAmpService;
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
public class MultiChannelAmpServiceImpl implements MultiChannelAmpService {

    private final MultiChannelAmplifierRepository repository;
    private final ModelMapper modelMapper;
    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public MultiChannelAmpServiceImpl(MultiChannelAmplifierRepository repository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddMultiChannelAmpDTO createNewAddMultiChannelAmpDTO() {
        return new AddMultiChannelAmpDTO();
    }

    @Override
    public long addDevice(AddMultiChannelAmpDTO addDeviceDTO) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            checkEntityExists(getBrand(addDeviceDTO), getModel(addDeviceDTO));
            MultiChannelAmplifier entity = mapToDevice(addDeviceDTO);
            long deviceId = repository.save(entity).getId();

            log.info("User with id ({}) and username ({}) added device with ID ({}), brand ({}), and model ({}).",
                    user.getId(), user.getUsername(), deviceId, getBrand(addDeviceDTO), getModel(addDeviceDTO));

            return deviceId;
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public long editDevice(AddMultiChannelAmpDTO addDeviceDTO) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            MultiChannelAmplifier entity = mapEditedDevice(addDeviceDTO);
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
            Optional<MultiChannelAmplifier> deviceOptional = repository.findById(deviceId);

            if (deviceOptional.isPresent()) {
                MultiChannelAmplifier device = deviceOptional.get();
                repository.deleteById(deviceId);

                log.info("User with id ({}) and username ({}) deleted device with ID ({}), brand ({}), and model ({}).",
                        user.getId(), user.getUsername(), deviceId, device.getBrand(), device.getModel());
            }
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public MultiChannelAmpDetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new DeviceNotFoundException("Device with id " + id + " not found!", id));
    }

    @Override
    public MultiChannelAmpDetailsHelperDTO getDeviceDetailsHelper(Long id) {
        MultiChannelAmpDetailsDTO deviceDetails = getDeviceDetails(id);
        return new MultiChannelAmpDetailsHelperDTO(deviceDetails);
    }

    @Override
    public List<MultiChannelAmpSummaryDTO> getAllDeviceSummary() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(MultiChannelAmplifier::getLikes)
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

        Optional<MultiChannelAmplifier> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            MultiChannelAmplifier entity = optionalEntity.get();
            addLikeToEntity(entity, user);
            repository.save(entity);
        } else {
            throw new DeviceNotFoundException("Device with id " + id + " not found!", id);
        }
    }

    @Override
    public void updateDeviceImageUrls(String oldUrl, String newUrl) {
        List<MultiChannelAmplifier> multiChannelAmplifiers = repository.findByImagesContaining(oldUrl);

        for (MultiChannelAmplifier multiChannelAmplifier : multiChannelAmplifiers) {
            List<String> images = multiChannelAmplifier.getImages();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).equals(oldUrl)) {
                    images.set(i, newUrl);
                }
            }
            multiChannelAmplifier.setImages(images);
            repository.save(multiChannelAmplifier);
        }
    }

    private MultiChannelAmplifier mapToDevice(AddMultiChannelAmpDTO addDeviceDTO) throws JsonProcessingException {
        createImageListDetailsDTO(addDeviceDTO);
        return modelMapper.map(addDeviceDTO, MultiChannelAmplifier.class);
    }

    private MultiChannelAmplifier mapEditedDevice(AddMultiChannelAmpDTO addMultiChannelAmpDTO) throws JsonProcessingException {
        createImageListDetailsDTO(addMultiChannelAmpDTO);
        return modelMapper.map(addMultiChannelAmpDTO, MultiChannelAmplifier.class);
    }

    private void createImageListDetailsDTO(AddMultiChannelAmpDTO addMultiChannelAmpDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addMultiChannelAmpDTO.getImages());
        imageListDetailsDTO.setTableName("multi_channel_amplifier_images");
        imageProducer.sendMessage(imageListDetailsDTO);
    }

    private MultiChannelAmpDetailsDTO toDetailsDTO(MultiChannelAmplifier multiChannelAmplifier) {
        MultiChannelAmpDetailsDTO multiChannelAmpDetailsDTO = modelMapper.map(multiChannelAmplifier, MultiChannelAmpDetailsDTO.class);
        multiChannelAmpDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return multiChannelAmpDetailsDTO;
    }

    private MultiChannelAmpSummaryDTO toSummaryDTO(MultiChannelAmplifier multiChannelAmplifier) {
        MultiChannelAmpSummaryDTO multiChannelAmpSummaryDTO = modelMapper.map(multiChannelAmplifier, MultiChannelAmpSummaryDTO.class);
        multiChannelAmpSummaryDTO.setLikes(multiChannelAmplifier.getLikes());
        return multiChannelAmpSummaryDTO;
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void addLikeToEntity(MultiChannelAmplifier entity, UserEntity user) {
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
        Optional<MultiChannelAmplifier> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale()
            );
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<MultiChannelAmplifier> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    private String getBrand(AddMultiChannelAmpDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    private String getModel(AddMultiChannelAmpDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }
}