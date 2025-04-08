package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.*;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.images.MidRangeImage;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MidRangeImageRepository;
import com.bg.bassheadsbg.repository.MidRangeRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MidRangeService;
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
public class MidRangeServiceImpl implements MidRangeService {

    private final MidRangeRepository midRangeRepository;
    private final MidRangeImageRepository midRangeImageRepository;
    private final ModelMapper modelMapper;
    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public MidRangeServiceImpl(MidRangeRepository midRangeRepository, MidRangeImageRepository midRangeImageRepository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.midRangeRepository = midRangeRepository;
        this.midRangeImageRepository = midRangeImageRepository;
        this.modelMapper = modelMapper;
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddMidRangeDTO createNewSpeaker() {
        return new AddMidRangeDTO();
    }

    @Transactional
    @Override
    public long addSpeaker(AddMidRangeDTO addMidRangeDTO) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MidRange midRange = modelMapper.map(addMidRangeDTO, MidRange.class);
        checkEntityExists(midRange.getBrand(), midRange.getModel());

        MidRange savedMidRange = midRangeRepository.save(midRange);

        updateSpeakerImages(user, midRange, addMidRangeDTO);

        ObjectLogger.logMessage(user,
                "added",
                midRange,
                midRange.getId(),
                midRange.getBrand(),
                midRange.getModel());

        return savedMidRange.getId();
    }

    @Transactional
    @Override
    public long editSpeaker(AddMidRangeDTO addMidRangeDTO, List<MultipartFile> multipartFiles) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MidRange entity = midRangeRepository.findById(addMidRangeDTO.getId())
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, addMidRangeDTO.getId()));
        if (addMidRangeDTO.getImageFiles() != null) {
            entity.getImageFiles().clear();
            updateSpeakerImages(user, entity, addMidRangeDTO);
        }

        entity = modelMapper.map(addMidRangeDTO, MidRange.class);

        MidRange savedMidRange = midRangeRepository.saveAndFlush(entity);

        ObjectLogger.logMessage(user,
                "edited",
                savedMidRange,
                savedMidRange.getId(),
                savedMidRange.getBrand(),
                savedMidRange.getModel());

        return savedMidRange.getId();
    }

    @Override
    public void deleteSpeaker(long speakerId) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Optional<MidRange> optMidRange = midRangeRepository.findById(speakerId);

        if (optMidRange.isPresent()) {
            MidRange midRange = optMidRange.get();
            midRangeRepository.deleteById(speakerId);
            ObjectLogger.logDeleteMessage(user,
                    midRange,
                    midRange.getBrand(),
                    midRange.getModel());
        }
    }

    @Transactional
    @Override
    public List<MidRangeSummaryDTO> getAllSpeakersSummarySorted() {
        return midRangeRepository.findAllMidRangesWithUserLikesCountOrderByBrandAndModel()
                .stream()
                .map(this::mapMidRangeToMidRangeSummaryDTO)
                .toList();
    }

    @Transactional
    @Override
    public MidRangeDetailsDTO getSpeakerDetails(Long id) {
        MidRange midRange = midRangeRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        Hibernate.initialize(midRange.getImageFiles());

        MidRangeDetailsDTO midRangeDetailsDTO = modelMapper.map(midRange, MidRangeDetailsDTO.class);

        midRangeDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        midRangeDetailsDTO.setImageFiles(midRange.getImageFiles()
                .stream().map(image -> Base64
                        .getEncoder().encodeToString(image.getImageData()))
                .collect(Collectors.toList()));

        return midRangeDetailsDTO;
    }

    @Transactional
    @Override
    public MidRangeDetailsHelperDTO getSpeakerDetailsHelper(Long id) {
        MidRangeDetailsDTO midRangeDetailsDTO = getSpeakerDetails(id);

        return new MidRangeDetailsHelperDTO(midRangeDetailsDTO);
    }

    @Override
    @Transactional
    public boolean likeSpeaker(Long id) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MidRange midRange = midRangeRepository.findHighRangeByUserLikes(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        boolean alreadyLiked = midRange.getUserLikes()
                .stream()
                .anyMatch(userLike -> userLike.getUsername().equals(user.getUsername()));

        if (alreadyLiked) {
            return false;
        }

        midRange.getUserLikes().add(user);
        midRangeRepository.save(midRange);

        ObjectLogger.logMessage(user,
                "liked",
                midRange,
                id,
                midRange.getBrand(),
                midRange.getModel());

        return true;
    }

    private void checkEntityExists(String brand, String model) {
        Optional<MidRange> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<MidRange> findByBrandAndModel(String brand, String model) {
        return midRangeRepository.findByBrandAndModel(brand, model);
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void updateSpeakerImages(UserEntity user, MidRange midRange, AddMidRangeDTO addMidRangeDTO) throws IOException {
        if (addMidRangeDTO.getImageFiles() != null && !addMidRangeDTO.getImageFiles().isEmpty()) {

            midRangeImageRepository.deleteByMidRange(midRange);

            List<MidRangeImage> midRangeImages = new ArrayList<>();

            List<MultipartFile> imageFiles = addMidRangeDTO.getImageFiles();
            for (int i = 0; i < imageFiles.size(); i++) {
                MultipartFile file = imageFiles.get(i);
                if (!file.isEmpty()) {
                    MidRangeImage midRangeImage = new MidRangeImage();
                    midRangeImage.setImageData(file.getBytes());
                    midRangeImage.setMidRange(midRange);
                    midRangeImages.add(midRangeImage);
                }
            }
            midRangeImageRepository.saveAll(midRangeImages);
        } else {
            ObjectLogger.logMessageWithoutImages(user,
                    "updated",
                    midRange,
                    midRange.getId(),
                    midRange.getBrand(),
                    midRange.getModel());
        }
    }

    private MidRangeSummaryDTO mapMidRangeToMidRangeSummaryDTO(MidRange midRange) {
        MidRangeSummaryDTO midRangeSummaryDTO = modelMapper.map(midRange, MidRangeSummaryDTO.class);
        midRangeSummaryDTO.setLikes(midRange.getLikes());
        byte[] image = midRange.getImageFiles().get(0).getImageData();
        midRangeSummaryDTO.setImageFile(Base64.getEncoder().encodeToString(image));
        return midRangeSummaryDTO;
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