package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.images.HighRangeImage;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;
import com.bg.bassheadsbg.repository.HighRangeImageRepository;
import com.bg.bassheadsbg.repository.HighRangeRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
import com.bg.bassheadsbg.util.ObjectLogger;
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

/**
 * Service implementation for managing high-range speakers.
 * This class provides methods for creating, editing, deleting, and retrieving high-range speakers,
 * also handling the likes and updating images.
 */
@Slf4j
@Service
public class HighRangeServiceImpl implements HighRangeService {

    private final HighRangeRepository highRangeRepository;
    private final HighRangeImageRepository highRangeImageRepository;
    private final ModelMapper modelMapper;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public HighRangeServiceImpl(HighRangeRepository highRangeRepository, HighRangeImageRepository highRangeImageRepository, ModelMapper modelMapper, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.highRangeRepository = highRangeRepository;
        this.highRangeImageRepository = highRangeImageRepository;
        this.modelMapper = modelMapper;
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
    public AddHighRangeDTO createNewSpeaker() {
        return new AddHighRangeDTO();
    }

    /**
     * Adds a new high-range device.
     *
     * @param addHighRangeDTO the DTO containing device information
     * @return the ID of the newly added device
     * @throws IOException if an error occurs while processing the images.
     */
    @Transactional
    @Override
    public long addSpeaker(AddHighRangeDTO addHighRangeDTO) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        HighRange highRange = modelMapper.map(addHighRangeDTO, HighRange.class);
        checkEntityExists(highRange.getBrand(), highRange.getModel());

        HighRange savedHighRange = highRangeRepository.save(highRange);

        updateSpeakerImages(user, highRange, addHighRangeDTO);

        ObjectLogger.logMessage(user,
                "added",
                highRange,
                highRange.getId(),
                highRange.getBrand(),
                highRange.getModel());

        return savedHighRange.getId();
    }

    /**
     * This method is used for editing an already existing high-range speaker.
     *
     * @param addHighRangeDTO the DTO, that should contain information about the updated speaker.
     * @return the ID of the edited high-range speaker.
     * @throws IOException if an error occurs while processing the images.
     */
    @Transactional
    @Override
    public long editSpeaker(AddHighRangeDTO addHighRangeDTO, List<MultipartFile> multipartFiles) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        HighRange entity = highRangeRepository.findById(addHighRangeDTO.getId())
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, addHighRangeDTO.getId()));
        if (addHighRangeDTO.getImageFiles() != null) {
            entity.getImageFiles().clear();
            updateSpeakerImages(user, entity, addHighRangeDTO);
        }

        entity = modelMapper.map(addHighRangeDTO, HighRange.class);

        HighRange savedHighRange = highRangeRepository.saveAndFlush(entity);

        ObjectLogger.logMessage(user,
                "edited",
                savedHighRange,
                savedHighRange.getId(),
                savedHighRange.getBrand(),
                savedHighRange.getModel());

        return savedHighRange.getId();
    }

    /**
     * Deletes a high-range speaker by ID.
     *
     * @param speakerId the ID of the speaker, that has to be deleted.
     */
    @Override
    public void deleteSpeaker(long speakerId) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Optional<HighRange> optHighRange = highRangeRepository.findById(speakerId);

        if (optHighRange.isPresent()) {
            HighRange highRange = optHighRange.get();
            highRangeRepository.deleteById(speakerId);
            ObjectLogger.logDeleteMessage(user,
                    highRange,
                    highRange.getBrand(),
                    highRange.getModel());
        }
    }

    /**
     * The method is used for retrieving a summary of all high-range speakers.
     *
     * @return a list of summaries of high-range speakers.
     */
    @Transactional
    @Override
    public List<HighRangeSummaryDTO> getAllSpeakersSummarySorted() {
        return highRangeRepository.findAllDevicesWithUserLikesCountOrderByBrandAndModel()
                .stream()
                .map(this::mapHighRangeToHighRangeSummaryDTO)
                .toList();
    }

    /**
     * The method is used to retrieve details of a high-range speaker by its ID.
     *
     * @param id the ID of the device
     * @return the details of the speaker
     * @throws DeviceNotFoundException if the speaker with the given ID is not found
     */
    @Transactional
    @Override
    public HighRangeDetailsDTO getSpeakerDetails(Long id) {
        HighRange highRange = highRangeRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        Hibernate.initialize(highRange.getImageFiles());

        HighRangeDetailsDTO highRangeDetailsDTO = modelMapper.map(highRange, HighRangeDetailsDTO.class);

        highRangeDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        highRangeDetailsDTO.setImageFiles(highRange.getImageFiles()
                .stream().map(image -> Base64
                        .getEncoder().encodeToString(image.getImageData()))
                .collect(Collectors.toList()));

        return highRangeDetailsDTO;
    }

    /**
     * Retrieves helper details for a high-range speaker by its ID.
     *
     * @param id the ID of the high-range speaker
     * @return the helper details of the speaker
     */
    @Transactional
    @Override
    public HighRangeDetailsHelperDTO getSpeakerDetailsHelper(Long id) {
        HighRangeDetailsDTO highRangeDetailsDTO = getSpeakerDetails(id);

        return new HighRangeDetailsHelperDTO(highRangeDetailsDTO);
    }

    /**
     * Method for liking a high-range speaker.
     *
     * @param id the ID of the speaker, that should be liked.
     * @return
     */
    @Override
    @Transactional
    public boolean likeSpeaker(Long id) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        HighRange highRange = highRangeRepository.findDeviceByUserLikes(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        boolean alreadyLiked = highRange.getUserLikes()
                .stream()
                .anyMatch(userLike -> userLike.getUsername().equals(user.getUsername()));

        if (alreadyLiked) {
            return false;
        }

        highRange.getUserLikes().add(user);
        highRangeRepository.save(highRange);

        ObjectLogger.logMessage(user,
                "liked",
                highRange,
                id,
                highRange.getBrand(),
                highRange.getModel());

        return true;
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
                    LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    /**
     * Finds a HighRange entity by its brand and model.
     *
     * @param brand the brand of the device
     * @param model the model of the device
     * @return an Optional, containing the HighRange entity if found.
     */
    private Optional<HighRange> findByBrandAndModel(String brand, String model) {
        return highRangeRepository.findByBrandAndModel(brand, model);
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    /**
     * This method is used to update the images of a high-range speakers.
     *
     * @param user            is used to pass user data to ObjectLogger class, that is used
     *                        to log messages in the console.
     * @param highRange       the high-range speaker, whose images will be set.
     * @param addHighRangeDTO the add high-range speaker dto, whose images
     *                        will be mapped to the high-range speaker.
     * @throws IOException if an error occurs while processing the images.
     */
    private void updateSpeakerImages(UserEntity user, HighRange highRange, AddHighRangeDTO addHighRangeDTO) throws IOException {
        if (addHighRangeDTO.getImageFiles() != null && !addHighRangeDTO.getImageFiles().isEmpty()) {

            highRangeImageRepository.deleteByDevice(highRange);

            List<HighRangeImage> highRangeImages = new ArrayList<>();

            List<MultipartFile> imageFiles = addHighRangeDTO.getImageFiles();
            for (int i = 0; i < imageFiles.size(); i++) {
                MultipartFile file = imageFiles.get(i);
                if (!file.isEmpty()) {
                    HighRangeImage highRangeImage = new HighRangeImage();
                    highRangeImage.setImageData(file.getBytes());
                    highRangeImage.setDevice(highRange);
                    highRangeImages.add(highRangeImage);
                }
            }
            highRangeImageRepository.saveAll(highRangeImages);
        } else {
            ObjectLogger.logMessageWithoutImages(user,
                    "updated",
                    highRange,
                    highRange.getId(),
                    highRange.getBrand(),
                    highRange.getModel());
        }
    }

    /**
     * This method is used to map HighRange to HighRangeSummaryDTO.
     *
     * @param highRange is used as a method parameter, that will be converted to a SummaryDTO.
     * @return HighRangeSummaryDTO with the needed image and user likes set properly.
     */

    private HighRangeSummaryDTO mapHighRangeToHighRangeSummaryDTO(HighRange highRange) {
        HighRangeSummaryDTO highRangeSummaryDTO = modelMapper.map(highRange, HighRangeSummaryDTO.class);
        highRangeSummaryDTO.setLikes(highRange.getLikes());
        byte[] image = highRange.getImageFiles().get(0).getImageData();
        highRangeSummaryDTO.setImageFile(Base64.getEncoder().encodeToString(image));
        return highRangeSummaryDTO;
    }

    /**
     * This static method returns the UserDetails, by using the Authentication interface
     * provided by spring security core.
     *
     * @return userDetails, if the user is authenticated
     * @throws UserNotAuthenticatedException, if the user is not authenticated.
     */
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