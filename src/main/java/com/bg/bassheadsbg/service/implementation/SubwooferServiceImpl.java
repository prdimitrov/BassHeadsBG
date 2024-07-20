package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.repository.SubwooferRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.SubwooferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubwooferServiceImpl extends CommonDeviceServiceImpl<AddSubwooferDTO, SubwooferDetailsDTO, SubwooferSummaryDTO, Subwoofer, SubwooferRepository>
        implements SubwooferService {

    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;

    public SubwooferServiceImpl(SubwooferRepository subwooferRepository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository) {
        super(subwooferRepository, modelMapper);
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
    }

    @Override
    protected Subwoofer mapToDevice(AddSubwooferDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("subwoofer_images");
        imageProducer.sendMessage(imageListDetailsDTO);
        return modelMapper.map(addDeviceDTO, Subwoofer.class);
    }

    @Override
    protected Subwoofer mapEditedDevice(AddSubwooferDTO addSubwooferDTO) {
        return modelMapper.map(addSubwooferDTO, Subwoofer.class);
    }

    @Override
    protected SubwooferDetailsDTO toDetailsDTO(Subwoofer subwoofer) {
        SubwooferDetailsDTO subwooferDetailsDTO = modelMapper.map(subwoofer, SubwooferDetailsDTO.class);
        subwooferDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return subwooferDetailsDTO;
    }

    @Override
    protected SubwooferSummaryDTO toSummaryDTO(Subwoofer subwoofer) {
        SubwooferSummaryDTO subwooferSummaryDTO = modelMapper.map(subwoofer, SubwooferSummaryDTO.class);
        subwooferSummaryDTO.setLikes(subwoofer.getLikes()); // Assuming 'likes' field exists
        return subwooferSummaryDTO;
    }

    @Override
    protected UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    protected void addLikeToEntity(Subwoofer entity, UserEntity user) {
        entity.getUserLikes().add(user);
    }

    @Override
    protected Optional<Subwoofer> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    @Override
    protected String getBrand(AddSubwooferDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    @Override
    protected String getModel(AddSubwooferDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }

    @Override
    public void updateDeviceImageUrls(String oldUrl, String newUrl) {
        List<Subwoofer> subwoofers = repository.findByImagesContaining(oldUrl);

        for (Subwoofer subwoofer : subwoofers) {
            List<String> images = subwoofer.getImages();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).equals(oldUrl)) {
                    images.set(i, newUrl);
                }
            }
            subwoofer.setImages(images);
            repository.save(subwoofer);
        }
    }
}