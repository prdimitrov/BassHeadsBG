package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.*;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MidRangeRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MidRangeService;
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
public class MidRangeServiceImpl implements MidRangeService {

    private final MidRangeRepository repository;
    private final ModelMapper modelMapper;
    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public MidRangeServiceImpl(MidRangeRepository repository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddMidRangeDTO createNewAddMidRangeDTO() {
        return new AddMidRangeDTO();
    }

    @Override
    public long addDevice(AddMidRangeDTO addDeviceDTO) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            checkEntityExists(getBrand(addDeviceDTO), getModel(addDeviceDTO));
            MidRange entity = mapToDevice(addDeviceDTO);
            long deviceId = repository.save(entity).getId();

            log.info("User with id ({}) and username ({}) added device with ID ({}), brand ({}), and model ({}).",
                    user.getId(), user.getUsername(), deviceId, getBrand(addDeviceDTO), getModel(addDeviceDTO));

            return deviceId;
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }
    
    @Override
    public long editDevice(AddMidRangeDTO addDeviceDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            MidRange entity = mapEditedDevice(addDeviceDTO);
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
            Optional<MidRange> deviceOptional = repository.findById(deviceId);

            if (deviceOptional.isPresent()) {
                MidRange device = deviceOptional.get();
                repository.deleteById(deviceId);

                log.info("User with id ({}) and username ({}) deleted device with ID ({}), brand ({}), and model ({}).",
                        user.getId(), user.getUsername(), deviceId, device.getBrand(), device.getModel());
            } else {
                log.warn("User with id ({}) and username ({}) attempted to delete a device with ID ({}), but it was not found.",
                        user.getId(), user.getUsername(), deviceId);
            }
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public MidRangeDetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new DeviceNotFoundException("Device with id " + id + " not found!", id));
    }

    @Override
    public MidRangeDetailsHelperDTO getDeviceDetailsHelper(Long id) {
        MidRangeDetailsDTO deviceDetails = getDeviceDetails(id);
        return new MidRangeDetailsHelperDTO(deviceDetails);
    }

    @Override
    public List<MidRangeSummaryDTO> getAllDeviceSummary() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(MidRange::getLikes)
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

        Optional<MidRange> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            MidRange entity = optionalEntity.get();
            addLikeToEntity(entity, user);
            repository.save(entity);
        } else {
            throw new DeviceNotFoundException("Device with id " + id + " not found!", id);
        }
    }

    @Override
    public void updateDeviceImageUrls(String oldUrl, String newUrl) {
        List<MidRange> midRanges = repository.findByImagesContaining(oldUrl);

        for (MidRange midRange : midRanges) {
            List<String> images = midRange.getImages();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).equals(oldUrl)) {
                    images.set(i, newUrl);
                }
            }
            midRange.setImages(images);
            repository.save(midRange);
        }
    }

    private MidRange mapToDevice(AddMidRangeDTO addDeviceDTO) throws JsonProcessingException {
        createImageListDetailsDTO(addDeviceDTO);
        return modelMapper.map(addDeviceDTO, MidRange.class);
    }

    private MidRange mapEditedDevice(AddMidRangeDTO addMidRangeDTO) {
        return modelMapper.map(addMidRangeDTO, MidRange.class);
    }

    private void createImageListDetailsDTO(AddMidRangeDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("mid_range_images");
        imageProducer.sendMessage(imageListDetailsDTO);    }

    private MidRangeDetailsDTO toDetailsDTO(MidRange midRange) {
        MidRangeDetailsDTO midRangeDetailsDTO = modelMapper.map(midRange, MidRangeDetailsDTO.class);
        midRangeDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return midRangeDetailsDTO;
    }

    private MidRangeSummaryDTO toSummaryDTO(MidRange midRange) {
        MidRangeSummaryDTO midRangeSummaryDTO = modelMapper.map(midRange, MidRangeSummaryDTO.class);
        midRangeSummaryDTO.setLikes(midRange.getLikes());
        return midRangeSummaryDTO;
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void addLikeToEntity(MidRange entity, UserEntity user) {
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
        Optional<MidRange> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale()
            );
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<MidRange> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    private String getBrand(AddMidRangeDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    private String getModel(AddMidRangeDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }
}