package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.entity.images.SubwooferImage;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.SubwooferDetailsHelperDTO;
import com.bg.bassheadsbg.repository.SubwooferImageRepository;
import com.bg.bassheadsbg.repository.SubwooferRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.SubwooferService;
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
public class SubwooferServiceImpl implements SubwooferService {

    private final SubwooferRepository subwooferRepository;
    private final SubwooferImageRepository subwooferImageRepository;
    private final ModelMapper modelMapper;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public SubwooferServiceImpl(SubwooferRepository subwooferRepository, SubwooferImageRepository subwooferImageRepository, ModelMapper modelMapper, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.subwooferRepository = subwooferRepository;
        this.subwooferImageRepository = subwooferImageRepository;
        this.modelMapper = modelMapper;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddSubwooferDTO createNewSpeaker() {
        return new AddSubwooferDTO();
    }

    @Transactional
    @Override
    public long addSpeaker(AddSubwooferDTO addSubwooferDTO) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Subwoofer subwoofer = modelMapper.map(addSubwooferDTO, Subwoofer.class);
        checkEntityExists(subwoofer.getBrand(), subwoofer.getModel());

        Subwoofer savedSubwoofer = subwooferRepository.save(subwoofer);

        updateSpeakerImages(user, subwoofer, addSubwooferDTO);

        ObjectLogger.logMessage(user,
                "added",
                subwoofer,
                subwoofer.getId(),
                subwoofer.getBrand(),
                subwoofer.getModel());

        return savedSubwoofer.getId();
    }

    @Transactional
    @Override
    public long editSpeaker(AddSubwooferDTO addSubwooferDTO, List<MultipartFile> multipartFiles) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Subwoofer entity = subwooferRepository.findById(addSubwooferDTO.getId())
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, addSubwooferDTO.getId()));
        if (addSubwooferDTO.getImageFiles() != null) {
            entity.getImageFiles().clear();
            updateSpeakerImages(user, entity, addSubwooferDTO);
        }

        entity = modelMapper.map(addSubwooferDTO, Subwoofer.class);

        Subwoofer savedSubwoofer = subwooferRepository.saveAndFlush(entity);

        ObjectLogger.logMessage(user,
                "edited",
                savedSubwoofer,
                savedSubwoofer.getId(),
                savedSubwoofer.getBrand(),
                savedSubwoofer.getModel());

        return savedSubwoofer.getId();
    }

    @Override
    public void deleteSpeaker(long speakerId) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Optional<Subwoofer> optSubwoofer = subwooferRepository.findById(speakerId);

        if (optSubwoofer.isPresent()) {
            Subwoofer subwoofer = optSubwoofer.get();
            subwooferRepository.deleteById(speakerId);
            ObjectLogger.logDeleteMessage(user,
                    subwoofer,
                    subwoofer.getBrand(),
                    subwoofer.getModel());
        }
    }

    @Transactional
    @Override
    public List<SubwooferSummaryDTO> getAllSpeakersSummarySorted() {
        return subwooferRepository.findAllDevicesWithUserLikesCountOrderByBrandAndModel()
                .stream()
                .map(this::mapSubwooferToSubwooferSummaryDTO)
                .toList();
    }

    @Transactional
    @Override
    public SubwooferDetailsDTO getSpeakerDetails(Long id) {
        Subwoofer subwoofer = subwooferRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        Hibernate.initialize(subwoofer.getImageFiles());

        SubwooferDetailsDTO subwooferDetailsDTO = modelMapper.map(subwoofer, SubwooferDetailsDTO.class);

        subwooferDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        subwooferDetailsDTO.setImageFiles(subwoofer.getImageFiles()
                .stream().map(image -> Base64
                        .getEncoder().encodeToString(image.getImageData()))
                .collect(Collectors.toList()));

        return subwooferDetailsDTO;
    }

    @Transactional
    @Override
    public SubwooferDetailsHelperDTO getSpeakerDetailsHelper(Long id) {
        SubwooferDetailsDTO subwooferDetailsDTO = getSpeakerDetails(id);

        return new SubwooferDetailsHelperDTO(subwooferDetailsDTO);
    }

    @Override
    @Transactional
    public boolean likeSpeaker(Long id) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Subwoofer subwoofer = subwooferRepository.findDeviceByUserLikes(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        boolean alreadyLiked = subwoofer.getUserLikes()
                .stream()
                .anyMatch(userLike -> userLike.getUsername().equals(user.getUsername()));

        if (alreadyLiked) {
            return false;
        }

        subwoofer.getUserLikes().add(user);
        subwooferRepository.save(subwoofer);

        ObjectLogger.logMessage(user,
                "liked",
                subwoofer,
                id,
                subwoofer.getBrand(),
                subwoofer.getModel());

        return true;
    }

    private void checkEntityExists(String brand, String model) {
        Optional<Subwoofer> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<Subwoofer> findByBrandAndModel(String brand, String model) {
        return subwooferRepository.findByBrandAndModel(brand, model);
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void updateSpeakerImages(UserEntity user, Subwoofer subwoofer, AddSubwooferDTO addSubwooferDTO) throws IOException {
        if (addSubwooferDTO.getImageFiles() != null && !addSubwooferDTO.getImageFiles().isEmpty()) {

            subwooferImageRepository.deleteByDevice(subwoofer);

            List<SubwooferImage> subwooferImages = new ArrayList<>();

            List<MultipartFile> imageFiles = addSubwooferDTO.getImageFiles();
            for (int i = 0; i < imageFiles.size(); i++) {
                MultipartFile file = imageFiles.get(i);
                if (!file.isEmpty()) {
                    SubwooferImage subwooferImage = new SubwooferImage();
                    subwooferImage.setImageData(file.getBytes());
                    subwooferImage.setDevice(subwoofer);
                    subwooferImages.add(subwooferImage);
                }
            }
            subwooferImageRepository.saveAll(subwooferImages);
        } else {
            ObjectLogger.logMessageWithoutImages(user,
                    "updated",
                    subwoofer,
                    subwoofer.getId(),
                    subwoofer.getBrand(),
                    subwoofer.getModel());
        }
    }

    private SubwooferSummaryDTO mapSubwooferToSubwooferSummaryDTO(Subwoofer subwoofer) {
        SubwooferSummaryDTO subwooferSummaryDTO = modelMapper.map(subwoofer, SubwooferSummaryDTO.class);
        subwooferSummaryDTO.setLikes(subwoofer.getLikes());
        byte[] image = subwoofer.getImageFiles().get(0).getImageData();
        subwooferSummaryDTO.setImageFile(Base64.getEncoder().encodeToString(image));
        return subwooferSummaryDTO;
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