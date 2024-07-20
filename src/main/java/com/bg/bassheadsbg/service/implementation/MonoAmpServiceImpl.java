package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MonoAmpServiceImpl extends CommonDeviceServiceImpl<AddMonoAmpDTO, MonoAmpDetailsDTO, MonoAmpSummaryDTO, MonoAmplifier, MonoAmplifierRepository>
        implements MonoAmpService {

    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;

    public MonoAmpServiceImpl(MonoAmplifierRepository monoAmplifierRepository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository) {
        super(monoAmplifierRepository, modelMapper);
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
    }

    @Override
    protected MonoAmplifier mapToDevice(AddMonoAmpDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("mono_amplifier_images");
        imageProducer.sendMessage(imageListDetailsDTO);
        return modelMapper.map(addDeviceDTO, MonoAmplifier.class);
    }

    @Override
    protected MonoAmplifier mapEditedDevice(AddMonoAmpDTO addMonoAmpDTO) {
        return modelMapper.map(addMonoAmpDTO, MonoAmplifier.class);
    }

    @Override
    protected MonoAmpDetailsDTO toDetailsDTO(MonoAmplifier monoAmplifier) {
        MonoAmpDetailsDTO monoAmpDetailsDTO = modelMapper.map(monoAmplifier, MonoAmpDetailsDTO.class);
        monoAmpDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return monoAmpDetailsDTO;
    }


    @Override
    protected MonoAmpSummaryDTO toSummaryDTO(MonoAmplifier monoAmplifier) {
        MonoAmpSummaryDTO monoAmpSummaryDTO = modelMapper.map(monoAmplifier, MonoAmpSummaryDTO.class);
        monoAmpSummaryDTO.setLikes(monoAmplifier.getLikes());
        return monoAmpSummaryDTO;
    }

    public void likeDevice(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        UserEntity user = null;
        if (principal instanceof UserDetails userDetails) {
            Optional<UserEntity> optUser = userRepository.findByUsername(userDetails.getUsername());

            if (optUser.isPresent()) {
                user = optUser.get();
            } else {
                throw new RuntimeException("User not found!");
            }
        } else {
            throw new RuntimeException("User not authenticated!");
        }

        Optional<MonoAmplifier> optionalAmplifier = repository.findById(id);
        if (optionalAmplifier.isPresent()) {
            MonoAmplifier amplifier = optionalAmplifier.get();

            Set<UserEntity> userLikes = amplifier.getUserLikes();
            userLikes.add(user); // Add UserEntity instead of userId

            repository.save(amplifier);
        } else {
            throw new DeviceNotFoundException("Device with id " + id + " not found!", id);
        }
    }

    @Override
    protected Optional<MonoAmplifier> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    @Override
    protected String getBrand(AddMonoAmpDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    @Override
    protected String getModel(AddMonoAmpDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }

    @Override
    public void updateDeviceImageUrls(String oldUrl, String newUrl) {
        List<MonoAmplifier> monoAmplifiers = repository.findByImagesContaining(oldUrl);

        for (MonoAmplifier monoAmplifier : monoAmplifiers) {
            List<String> images = monoAmplifier.getImages();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).equals(oldUrl)) {
                    images.set(i, newUrl);
                }
            }
            monoAmplifier.setImages(images);
            repository.save(monoAmplifier);
        }
    }
}