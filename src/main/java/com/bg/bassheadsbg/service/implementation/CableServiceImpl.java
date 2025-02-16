package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddCableDTO;
import com.bg.bassheadsbg.model.dto.details.CableDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.CableSummaryDTO;
import com.bg.bassheadsbg.model.entity.images.CableImage;
import com.bg.bassheadsbg.model.entity.other.Cable;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.repository.CableImageRepository;
import com.bg.bassheadsbg.repository.CableRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.CableService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CableServiceImpl implements CableService {

    private final CableRepository cableRepository;
    private final CableImageRepository cableImageRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public CableServiceImpl(CableRepository cableRepository, CableImageRepository cableImageRepository, ModelMapper modelMapper, UserRepository userRepository, MessageSource messageSource) {
        this.cableRepository = cableRepository;
        this.cableImageRepository = cableImageRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public AddCableDTO createNewCableDTO() {
        return new AddCableDTO();
    }

    @Transactional
    public long addCable(AddCableDTO addCableDTO, List<MultipartFile> multipartFiles) throws IOException {
        Object principal = getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());

            Cable cable = modelMapper.map(addCableDTO, Cable.class);
            checkEntityExists(cable.getBrand(), cable.getModel());

            Cable savedCable = cableRepository.save(cable);

            List<CableImage> cableImages = new ArrayList<>();
            for (MultipartFile file : multipartFiles) {
                if (!file.isEmpty()) {
                    CableImage cableImage = new CableImage();
                    cableImage.setImageData(file.getBytes());
                    cableImage.setCable(savedCable);
                    cableImages.add(cableImage);
                }
            }

            cableImageRepository.saveAll(cableImages);

            log.info("User with id ({}) and username ({}) added cable with ID ({}), brand ({}), and model ({}).",
                    user.getId(), user.getUsername(), savedCable.getId(),
                    savedCable.getBrand(), savedCable.getModel());

            return savedCable.getId();
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    public List<byte[]> getCableImages(Long cableId) {
        Optional<Cable> optCable = getCable(cableId);

        if (optCable.isPresent()) {
            Cable cable = optCable.get();
            // Extract the byte[] data from the CableImage entities
            return cable.getImageFiles().stream()
                    .map(CableImage::getImageData)  // Get the byte[] from each CableImage entity
                    .toList();
        }

        return null;  // Return null if cable is not found
    }

    @Override
    public Optional<Cable> getCable(Long id) {
        return cableRepository.findById(id);
    }

    @Override
    public long editCable(AddCableDTO addCableDTO) {
        Object principal = getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            Cable entity = modelMapper.map(addCableDTO, Cable.class);
            checkEntityExists(entity.getBrand(), entity.getModel());
            long cableId = cableRepository.save(entity).getId();

            log.info("User with id ({}) and username ({}) edited device with ID ({}), brand ({}) and model ({}).",
                    user.getId(), user.getUsername(), entity.getId(), entity.getBrand(), entity.getMaterial());

            return cableId;
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public void deleteCable(long cableId) {
        Object principal = getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            UserEntity user = getUserEntity(userDetails.getUsername());
            Optional<Cable> optCable = cableRepository.findById(cableId);

            if (optCable.isPresent()) {
                Cable cable = optCable.get();
                cableRepository.deleteById(cableId);
                log.info("User with id ({}) and username ({}) deleted cable with ID ({}), brand ({}) and model ({})",
                        user.getId(),
                        user.getUsername(),
                        cableId,
                        cable.getBrand(),
                        cable.getModel());
            }
        } else {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }
    }

    @Override
    public List<CableSummaryDTO> getAllCableSummary() {
        return cableRepository.findAll()
                .stream()
                .sorted()
                .map(this::toSummaryDTO)
                .toList();
    }

    @Override
    public CableDetailsDTO getCableDetails(Long id) {
        return cableRepository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new DeviceNotFoundException("Cable with id " + id + " not found!", id));
    }

    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }

    private void checkEntityExists(String brand, String model) {
        Optional<Cable> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            String errorMessage = messageSource.getMessage(
                    ExceptionMessages.DEVICE_ALREADY_EXISTS,
                    null,
                    LocaleContextHolder.getLocale());
            throw new DeviceAlreadyExistsException(errorMessage);
        }
    }

    private Optional<Cable> findByBrandAndModel(String brand, String model) {
        return cableRepository.findByBrandAndModel(brand, model);
    }

    private static Object getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }

    private CableSummaryDTO toSummaryDTO(Cable cable) {
        CableSummaryDTO cableSummaryDTO = modelMapper.map(cable, CableSummaryDTO.class);
        return cableSummaryDTO;
    }

    private CableDetailsDTO toDetailsDTO(Cable cable) {
        CableDetailsDTO cableDetailsDTO = modelMapper.map(cable, CableDetailsDTO.class);
        return cableDetailsDTO;
    }
}
