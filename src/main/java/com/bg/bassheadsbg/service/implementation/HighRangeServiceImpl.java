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

/**
 * Service implementation for managing high-range speakers.
 * This class provides methods for creating, editing, deleting, and retrieving high-range speakers,
 * also handling the likes and updating image URLs.
 */
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

    /**
     * Creates a new instance of AddHighRangeDTO.
     *
     * @return a new AddHighRangeDTO object
     */
    @Override
    public AddHighRangeDTO createNewAddHighRangeDTO() {
        return new AddHighRangeDTO();
    }

    /**
     * Adds a new high-range device.
     *
     * @param addDeviceDTO the DTO containing device information
     * @return the ID of the newly added device
     * @throws JsonProcessingException if an error occurs while processing JSON data (the images)
     */
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

    /**
     * This method is used for editing an already existing high-range speaker.
     *
     * @param addDeviceDTO the DTO, that should contain information about the updated speaker.
     * @return the ID of the edited high-range speaker.
     */
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

    /**
     * Deletes a high-range speaker by ID.
     *
     * @param deviceId the ID of the speaker, that has to be deleted
     */
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

    /**
     * The method is used to retrieve details of a high-range speaker by its ID.
     *
     * @param id the ID of the device
     * @return the details of the speaker
     * @throws DeviceNotFoundException if the speaker with the given ID is not found
     */
    @Override
    public HighRangeDetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new DeviceNotFoundException("Device with id " + id + " not found!", id));
    }

    /**
     * Retrieves helper details for a high-range speaker by its ID.
     *
     * @param id the ID of the high-range speaker
     * @return the helper details of the speaker
     */
    @Override
    public HighRangeDetailsHelperDTO getDeviceDetailsHelper(Long id) {
        HighRangeDetailsDTO deviceDetails = getDeviceDetails(id);
        return new HighRangeDetailsHelperDTO(deviceDetails);
    }

    /**
     * The method is used for retrieving a summary of all high-range speakers.
     *
     * @return a list of summaries of high-range speakers.
     */
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

    /**
     * Method for liking a high-range speaker.
     *
     * @param id the ID of the speaker, that should be liked
     */
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

    /**
     * Updates image URLs for high-range devices that contain the old URL.
     *
     * @param oldUrl the old image URL
     * @param newUrl the new image URL
     */
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

    /**
     * Maps an AddHighRangeDTO to a HighRange entity.
     *
     * @param addDeviceDTO the add DTO, that should be mapped
     * @return the mapped HighRange entity
     * @throws JsonProcessingException if an error occurs while processing the JSON data
     */
    private HighRange mapToDevice(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException {
        createImageListDetailsDTO(addDeviceDTO);
        return modelMapper.map(addDeviceDTO, HighRange.class);
    }

    /**
     * Maps an AddHighRangeDTO to an edited HighRange entity.
     *
     * @param addHighRangeDTO the DTO to map
     * @return the mapped HighRange entity
     */
    private HighRange mapEditedDevice(AddHighRangeDTO addHighRangeDTO) {
        return modelMapper.map(addHighRangeDTO, HighRange.class);
    }

    /**
     * Creates and sends an ImageListDetailsDTO message to update image.
     *
     * @param addDeviceDTO the data transfer object, that contains image URLs
     * @throws JsonProcessingException if an error occurs while processing the JSON data
     */
    private void createImageListDetailsDTO(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("high_range_images");
        imageProducer.sendMessage(imageListDetailsDTO);
    }

    /**
     * The method is used to map a HighRange entity to a HighRangeDetailsDTO.
     *
     * @param highRange the HighRange entity
     * @return the HighRangeDetailsDTO
     */
    private HighRangeDetailsDTO toDetailsDTO(HighRange highRange) {
        HighRangeDetailsDTO highRangeDetailsDTO = modelMapper.map(highRange, HighRangeDetailsDTO.class);
        highRangeDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return highRangeDetailsDTO;
    }

    /**
     * This method is used to map a HighRange entity to a HighRangeSummaryDTO.
     *
     * @param highRange the HighRange entity
     * @return the HighRangeSummaryDTO
     */
    private HighRangeSummaryDTO toSummaryDTO(HighRange highRange) {
        HighRangeSummaryDTO highRangeSummaryDTO = modelMapper.map(highRange, HighRangeSummaryDTO.class);
        highRangeSummaryDTO.setLikes(highRange.getLikes());
        return highRangeSummaryDTO;
    }

    /**
     * The method retrieves a UserEntity by username.
     *
     * @param username the username of the user
     * @return the UserEntity
     * @throws UserNotFoundException if the user is not found
     */
    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    /**
     * Adds a like to a HighRange speaker's List<UserEntity> from a UserEntity.
     *
     * @param entity the HighRange entity
     * @param user the UserEntity liking the device, that will be added in the list
     * @throws DeviceAlreadyLikedException if the user has already liked the device
     */
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

    /**
     * Checks if a high-range speaker with a given brand and model does already exist.
     *
     * @param brand the brand of the speaker
     * @param model the model of the speaker
     * @throws DeviceAlreadyExistsException if a device with the given brand and model already exists
     */
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

    /**
     * Finds a HighRange entity by its brand and model.
     *
     * @param brand the brand of the device
     * @param model the model of the device
     * @return an Optional containing the HighRange entity if found
     */
    private Optional<HighRange> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    /**
     * This method retrieves the brand from an AddHighRangeDTO.
     *
     * @param addDeviceDTO the DTO containing information of the speaker
     * @return the brand of the speaker
     */
    private String getBrand(AddHighRangeDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    /**
     * This method retrieves the model from an AddHighRangeDTO.
     *
     * @param addDeviceDTO the DTO containing information of the speaker
     * @return the model of the speaker
     */
    private String getModel(AddHighRangeDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }
}