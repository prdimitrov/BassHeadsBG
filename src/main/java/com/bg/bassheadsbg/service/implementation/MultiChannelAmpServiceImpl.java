package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.*;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.model.entity.images.MultiChannelAmplifierImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MultiChannelAmpDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MultiChannelAmplifierImageRepository;
import com.bg.bassheadsbg.repository.MultiChannelAmplifierRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MultiChannelAmpService;
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
public class MultiChannelAmpServiceImpl implements MultiChannelAmpService {

    private final MultiChannelAmplifierRepository multiChannelAmplifierRepository;
    private final MultiChannelAmplifierImageRepository multiChannelAmplifierImageRepository;
    private final ModelMapper modelMapper;
    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public MultiChannelAmpServiceImpl(MultiChannelAmplifierRepository multiChannelAmplifierRepository, MultiChannelAmplifierImageRepository multiChannelAmplifierImageRepository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository, MessageSource messageSource) {
        this.multiChannelAmplifierRepository = multiChannelAmplifierRepository;
        this.multiChannelAmplifierImageRepository = multiChannelAmplifierImageRepository;
        this.modelMapper = modelMapper;
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddMultiChannelAmpDTO createNewAmplifier() {
        return new AddMultiChannelAmpDTO();
    }

    @Transactional
    @Override
    public long addAmplifier(AddMultiChannelAmpDTO addMultiChannelAmpDTO) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MultiChannelAmplifier multiChannelAmplifier = modelMapper.map(addMultiChannelAmpDTO, MultiChannelAmplifier.class);
        checkEntityExists(multiChannelAmplifier.getBrand(), multiChannelAmplifier.getModel());

        MultiChannelAmplifier savedMultiChannelAmplifier = multiChannelAmplifierRepository.save(multiChannelAmplifier);

        updateAmplifierImages(user, multiChannelAmplifier, addMultiChannelAmpDTO);

        ObjectLogger.logMessage(user,
                "added",
                multiChannelAmplifier,
                multiChannelAmplifier.getId(),
                multiChannelAmplifier.getBrand(),
                multiChannelAmplifier.getModel());

        return savedMultiChannelAmplifier.getId();
    }

    @Transactional
    @Override
    public long editAmplifier(AddMultiChannelAmpDTO addMultiChannelAmpDTO, List<MultipartFile> multipartFiles) throws IOException {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MultiChannelAmplifier entity = multiChannelAmplifierRepository.findById(addMultiChannelAmpDTO.getId())
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, addMultiChannelAmpDTO.getId()));
        if (addMultiChannelAmpDTO.getImageFiles() != null) {
            entity.getImageFiles().clear();
            updateAmplifierImages(user, entity, addMultiChannelAmpDTO);
        }

        entity = modelMapper.map(addMultiChannelAmpDTO, MultiChannelAmplifier.class);

        MultiChannelAmplifier savedMultiChannelAmplifier = multiChannelAmplifierRepository.saveAndFlush(entity);

        ObjectLogger.logMessage(user,
                "edited",
                savedMultiChannelAmplifier,
                savedMultiChannelAmplifier.getId(),
                savedMultiChannelAmplifier.getBrand(),
                savedMultiChannelAmplifier.getModel());

        return savedMultiChannelAmplifier.getId();
    }

    @Override
    public void deleteAmplifier(long amplifierId) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        Optional<MultiChannelAmplifier> optMultiChannelAmplifier = multiChannelAmplifierRepository.findById(amplifierId);

        if (optMultiChannelAmplifier.isPresent()) {
            MultiChannelAmplifier multiChannelAmplifier = optMultiChannelAmplifier.get();
            multiChannelAmplifierRepository.deleteById(amplifierId);
            ObjectLogger.logDeleteMessage(user,
                    multiChannelAmplifier,
                    multiChannelAmplifier.getBrand(),
                    multiChannelAmplifier.getModel());
        }
    }

    @Transactional
    @Override
    public List<MultiChannelAmpSummaryDTO> getAllAmplifiersSummarySorted() {
       List<MultiChannelAmplifier> multiChannelAmplifiersList = multiChannelAmplifierRepository.findAllMultiChannelAmpsUserLikesCountOrderByBrandAndModel();
       return multiChannelAmplifiersList.stream()
               .map(this::mapMultiChannelAmpToMultiChannelAmpSummaryDTO)
               .toList();
    }

    @Transactional
    @Override
    public MultiChannelAmpDetailsDTO getAmplifierDetails(Long id) {
        MultiChannelAmplifier multiChannelAmplifier = multiChannelAmplifierRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        Hibernate.initialize(multiChannelAmplifier.getImageFiles());

        MultiChannelAmpDetailsDTO multiChannelAmplifierDetailsDTO = modelMapper.map(multiChannelAmplifier, MultiChannelAmpDetailsDTO.class);

        multiChannelAmplifierDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        multiChannelAmplifierDetailsDTO.setImageFiles(multiChannelAmplifier.getImageFiles()
                .stream().map(image -> Base64
                        .getEncoder().encodeToString(image.getImageData()))
                .collect(Collectors.toList()));

        return multiChannelAmplifierDetailsDTO;
    }

    @Transactional
    @Override
    public MultiChannelAmpDetailsHelperDTO getAmplifierDetailsHelper(Long id) {
        MultiChannelAmpDetailsDTO multiChannelAmplifierDetailsDTO = getAmplifierDetails(id);

        return new MultiChannelAmpDetailsHelperDTO(multiChannelAmplifierDetailsDTO);
    }

    @Override
    public void likeAmplifier(Long id) {
        UserEntity user = getUserEntity(getPrincipal().getUsername());

        MultiChannelAmplifier multiChannelAmplifier = multiChannelAmplifierRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));

        List<UserEntity> userLikes = multiChannelAmplifier.getUserLikes();

        for (UserEntity userLike : userLikes) {
            if (user.getId() == userLike.getId()) {
                String errorMessage = messageSource.getMessage(
                        ExceptionMessages.DEVICE_ALREADY_LIKED,
                        null,
                        LocaleContextHolder.getLocale()
                );
                throw new DeviceAlreadyLikedException(errorMessage);
            }
        }

        userLikes.add(user);

        multiChannelAmplifierRepository.save(multiChannelAmplifier);

        ObjectLogger.logMessage(user,
                "liked",
                multiChannelAmplifier,
                id,
                multiChannelAmplifier.getBrand(),
                multiChannelAmplifier.getModel());
    }

    private void checkEntityExists(String brand, String model) {
        Optional<MultiChannelAmplifier> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<MultiChannelAmplifier> findByBrandAndModel(String brand, String model) {
        return multiChannelAmplifierRepository.findByBrandAndModel(brand, model);
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void updateAmplifierImages(UserEntity user, MultiChannelAmplifier multiChannelAmplifier, AddMultiChannelAmpDTO addMultiChannelAmpDTO) throws IOException {
        if (addMultiChannelAmpDTO.getImageFiles() != null && !addMultiChannelAmpDTO.getImageFiles().isEmpty()) {

            multiChannelAmplifierImageRepository.deleteByMultiChannelAmplifier(multiChannelAmplifier);

            List<MultiChannelAmplifierImage> multiChannelAmplifierImages = new ArrayList<>();

            List<MultipartFile> imageFiles = addMultiChannelAmpDTO.getImageFiles();
            for (int i = 0; i < imageFiles.size(); i++) {
                MultipartFile file = imageFiles.get(i);
                if (!file.isEmpty()) {
                    MultiChannelAmplifierImage multiChannelAmplifierImage = new MultiChannelAmplifierImage();
                    multiChannelAmplifierImage.setImageData(file.getBytes());
                    multiChannelAmplifierImage.setMultiChannelAmplifier(multiChannelAmplifier);
                    multiChannelAmplifierImages.add(multiChannelAmplifierImage);
                }
            }
            multiChannelAmplifierImageRepository.saveAll(multiChannelAmplifierImages);
        } else {
            ObjectLogger.logMessageWithoutImages(user,
                    "updated",
                    multiChannelAmplifier,
                    multiChannelAmplifier.getId(),
                    multiChannelAmplifier.getBrand(),
                    multiChannelAmplifier.getModel());
        }
    }

    private MultiChannelAmpSummaryDTO mapMultiChannelAmpToMultiChannelAmpSummaryDTO(MultiChannelAmplifier multiChannelAmp) {
        MultiChannelAmpSummaryDTO multiChannelAmpSummaryDTO = modelMapper.map(multiChannelAmp, MultiChannelAmpSummaryDTO.class);
        multiChannelAmpSummaryDTO.setLikes(multiChannelAmp.getLikes());
        byte[] image = multiChannelAmp.getImageFiles().get(0).getImageData();
        multiChannelAmpSummaryDTO.setImageFile(Base64.getEncoder().encodeToString(image));
        return multiChannelAmpSummaryDTO;
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