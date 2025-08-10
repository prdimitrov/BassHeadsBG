package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.images.MonoAmplifierImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MonoAmplifierImageRepository;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
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
public class MonoAmpServiceImpl implements MonoAmpService {

    private final MonoAmplifierRepository monoAmplifierRepository;
    private final MonoAmplifierImageRepository monoAmplifierImageRepository;
    private final ModelMapper modelMapper;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public MonoAmpServiceImpl(MonoAmplifierRepository monoAmplifierRepository, MonoAmplifierImageRepository monoAmplifierImageRepository, ModelMapper modelMapper, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.monoAmplifierRepository = monoAmplifierRepository;
        this.monoAmplifierImageRepository = monoAmplifierImageRepository;
        this.modelMapper = modelMapper;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddMonoAmpDTO createNewAmplifier() {
        return new AddMonoAmpDTO();
    }

    @Transactional
    @Override
    public long addAmplifier(AddMonoAmpDTO addMonoAmpDTO) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MonoAmplifier monoAmplifier = modelMapper.map(addMonoAmpDTO, MonoAmplifier.class);
        checkEntityExists(monoAmplifier.getBrand(), monoAmplifier.getModel());

        MonoAmplifier savedMonoAmplifier = monoAmplifierRepository.save(monoAmplifier);

        updateAmplifierImages(user, monoAmplifier, addMonoAmpDTO);

        ObjectLogger.logMessage(user,
                "added",
                monoAmplifier,
                monoAmplifier.getId(),
                monoAmplifier.getBrand(),
                monoAmplifier.getModel());

        return savedMonoAmplifier.getId();
    }

    @Transactional
    @Override
    public long editAmplifier(AddMonoAmpDTO addMonoAmpDTO, List<MultipartFile> multipartFiles) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MonoAmplifier entity = monoAmplifierRepository.findById(addMonoAmpDTO.getId())
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, addMonoAmpDTO.getId()));
        if (addMonoAmpDTO.getImageFiles() != null) {
            entity.getImageFiles().clear();
            updateAmplifierImages(user, entity, addMonoAmpDTO);
        }

        entity = modelMapper.map(addMonoAmpDTO, MonoAmplifier.class);

        MonoAmplifier savedMonoAmplifier = monoAmplifierRepository.saveAndFlush(entity);

        ObjectLogger.logMessage(user,
                "edited",
                savedMonoAmplifier,
                savedMonoAmplifier.getId(),
                savedMonoAmplifier.getBrand(),
                savedMonoAmplifier.getModel());

        return savedMonoAmplifier.getId();
    }

    @Override
    public void deleteAmplifier(long amplifierId) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Optional<MonoAmplifier> optMonoAmplifier = monoAmplifierRepository.findById(amplifierId);

        if (optMonoAmplifier.isPresent()) {
            MonoAmplifier monoAmplifier = optMonoAmplifier.get();
            monoAmplifierRepository.deleteById(amplifierId);
            ObjectLogger.logDeleteMessage(user,
                    monoAmplifier,
                    monoAmplifier.getBrand(),
                    monoAmplifier.getModel());
        }
    }

    @Transactional
    @Override
    public List<MonoAmpSummaryDTO> getAllAmplifiersSummarySorted() {
        return monoAmplifierRepository.findAllDevicesWithUserLikesCountOrderByBrandAndModel()
                .stream()
                .map(this::mapMonoAmpToMonoAmpSummaryDTO)
                .toList();
    }

    @Transactional
    @Override
    public MonoAmpDetailsDTO getAmplifierDetails(Long id) {
        MonoAmplifier monoAmplifier = monoAmplifierRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        Hibernate.initialize(monoAmplifier.getImageFiles());

        MonoAmpDetailsDTO monoAmplifierDetailsDTO = modelMapper.map(monoAmplifier, MonoAmpDetailsDTO.class);

        monoAmplifierDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        monoAmplifierDetailsDTO.setImageFiles(monoAmplifier.getImageFiles()
                .stream().map(image -> Base64
                        .getEncoder().encodeToString(image.getImageData()))
                .collect(Collectors.toList()));

        return monoAmplifierDetailsDTO;
    }

    @Transactional
    @Override
    public MonoAmpDetailsHelperDTO getAmplifierDetailsHelper(Long id) {
        MonoAmpDetailsDTO monoAmplifierDetailsDTO = getAmplifierDetails(id);

        return new MonoAmpDetailsHelperDTO(monoAmplifierDetailsDTO);
    }

    @Override
    public boolean likeAmplifier(Long id) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MonoAmplifier monoAmplifier = monoAmplifierRepository.findDeviceByUserLikes(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        boolean alreadyLiked = monoAmplifier.getUserLikes()
                .stream()
                .anyMatch(userLike -> userLike.getUsername().equals(user.getUsername()));

        if (alreadyLiked) {
            return false;
        }

        monoAmplifier.getUserLikes().add(user);
        monoAmplifierRepository.save(monoAmplifier);

        ObjectLogger.logMessage(user,
                "liked",
                monoAmplifier,
                id,
                monoAmplifier.getBrand(),
                monoAmplifier.getModel());

        return true;
    }

    private void checkEntityExists(String brand, String model) {
        Optional<MonoAmplifier> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<MonoAmplifier> findByBrandAndModel(String brand, String model) {
        return monoAmplifierRepository.findByBrandAndModel(brand, model);
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void updateAmplifierImages(UserEntity user, MonoAmplifier monoAmplifier, AddMonoAmpDTO addMonoAmpDTO) throws IOException {
        if (addMonoAmpDTO.getImageFiles() != null && !addMonoAmpDTO.getImageFiles().isEmpty()) {

            monoAmplifierImageRepository.deleteByDevice(monoAmplifier);

            List<MonoAmplifierImage> monoAmplifierImages = new ArrayList<>();

            List<MultipartFile> imageFiles = addMonoAmpDTO.getImageFiles();
            for (int i = 0; i < imageFiles.size(); i++) {
                MultipartFile file = imageFiles.get(i);
                if (!file.isEmpty()) {
                    MonoAmplifierImage monoAmplifierImage = new MonoAmplifierImage();
                    monoAmplifierImage.setImageData(file.getBytes());
                    monoAmplifierImage.setDevice(monoAmplifier);
                    monoAmplifierImages.add(monoAmplifierImage);
                }
            }
            monoAmplifierImageRepository.saveAll(monoAmplifierImages);
        } else {
            ObjectLogger.logMessageWithoutImages(user,
                    "updated",
                    monoAmplifier,
                    monoAmplifier.getId(),
                    monoAmplifier.getBrand(),
                    monoAmplifier.getModel());
        }
    }

    private MonoAmpSummaryDTO mapMonoAmpToMonoAmpSummaryDTO(MonoAmplifier monoAmplifier) {
        MonoAmpSummaryDTO monoAmpSummaryDTO = modelMapper.map(monoAmplifier, MonoAmpSummaryDTO.class);
        monoAmpSummaryDTO.setLikes(monoAmplifier.getLikes());
        byte[] image = monoAmplifier.getImageFiles().get(0).getImageData();
        monoAmpSummaryDTO.setImageFile(Base64.getEncoder().encodeToString(image));
        return monoAmpSummaryDTO;
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