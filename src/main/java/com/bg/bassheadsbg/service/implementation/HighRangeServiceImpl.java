package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.*;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;
import com.bg.bassheadsbg.repository.HighRangeRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
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
public class HighRangeServiceImpl implements HighRangeService {

    private final HighRangeRepository repository;
    private final ModelMapper modelMapper;
    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public HighRangeServiceImpl(HighRangeRepository repository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddHighRangeDTO createNewAddHighRangeDTO() {
        return new AddHighRangeDTO();
    }

    @Override
    public long addDevice(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            checkEntityExists(getBrand(addDeviceDTO), getModel(addDeviceDTO));
            HighRange entity = mapToDevice(addDeviceDTO);
            long deviceId = repository.save(entity).getId();

            log.info("User with id ({}) and username ({}) added device with ID ({}), brand ({}), and model ({}).",
                    user.getId(), user.getUsername(), deviceId, getBrand(addDeviceDTO), getModel(addDeviceDTO));

            return deviceId;
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public long editDevice(AddHighRangeDTO addDeviceDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            HighRange entity = mapEditedDevice(addDeviceDTO);
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
            Optional<HighRange> deviceOptional = repository.findById(deviceId);

            if (deviceOptional.isPresent()) {
                HighRange device = deviceOptional.get();
                repository.deleteById(deviceId);

                log.info("User with id ({}) and username ({}) deleted device with ID ({}), brand ({}), and model ({}).",
                        user.getId(), user.getUsername(), deviceId, device.getBrand(), device.getModel());
            }
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public HighRangeDetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new DeviceNotFoundException("Device with id " + id + " not found!", id));
    }

    @Override
    public HighRangeDetailsHelperDTO getDeviceDetailsHelper(Long id) {
        HighRangeDetailsDTO deviceDetails = getDeviceDetails(id);
        return new HighRangeDetailsHelperDTO(deviceDetails);
    }

    @Override
    public List<HighRangeSummaryDTO> getAllDeviceSummary() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(HighRange::getLikes)
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

        Optional<HighRange> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            HighRange entity = optionalEntity.get();
            addLikeToEntity(entity, user);
            repository.save(entity);
        } else {
            throw new DeviceNotFoundException("Device with id " + id + " not found!", id);
        }
    }

    @Override
    public void updateDeviceImageUrls(String oldUrl, String newUrl) {
        List<HighRange> highRanges = repository.findByImagesContaining(oldUrl);

        for (HighRange highRange : highRanges) {
            List<String> images = highRange.getImages();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).equals(oldUrl)) {
                    images.set(i, newUrl);
                }
            }
            highRange.setImages(images);
            repository.save(highRange);
        }
    }

    private HighRange mapToDevice(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException {
        createImageListDetailsDTO(addDeviceDTO);
        return modelMapper.map(addDeviceDTO, HighRange.class);
    }

    private HighRange mapEditedDevice(AddHighRangeDTO addHighRangeDTO) {
        return modelMapper.map(addHighRangeDTO, HighRange.class);
    }

    private void createImageListDetailsDTO(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("high_range_images");
        imageProducer.sendMessage(imageListDetailsDTO);
    }

    private HighRangeDetailsDTO toDetailsDTO(HighRange highRange) {
        HighRangeDetailsDTO highRangeDetailsDTO = modelMapper.map(highRange, HighRangeDetailsDTO.class);
        highRangeDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return highRangeDetailsDTO;
    }

    private HighRangeSummaryDTO toSummaryDTO(HighRange highRange) {
        HighRangeSummaryDTO highRangeSummaryDTO = modelMapper.map(highRange, HighRangeSummaryDTO.class);
        highRangeSummaryDTO.setLikes(highRange.getLikes());
        return highRangeSummaryDTO;
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void addLikeToEntity(HighRange entity, UserEntity user) {
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
        Optional<HighRange> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale()
            );
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<HighRange> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    private String getBrand(AddHighRangeDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    private String getModel(AddHighRangeDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }
}