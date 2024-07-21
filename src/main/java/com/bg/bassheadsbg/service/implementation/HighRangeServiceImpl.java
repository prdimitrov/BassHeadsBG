package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyLikedException;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.repository.HighRangeRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class HighRangeServiceImpl extends CommonDeviceServiceImpl<AddHighRangeDTO, HighRangeDetailsDTO, HighRangeSummaryDTO, HighRange, HighRangeRepository>
        implements HighRangeService {

    private final ImageProducer imageProducer;
    private final ExRateService exRateService;
    private final UserRepository userRepository;

    public HighRangeServiceImpl(HighRangeRepository highRangeRepository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService, UserRepository userRepository) {
        super(highRangeRepository, modelMapper);
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
        this.userRepository = userRepository;
    }

    @Override
    protected HighRange mapToDevice(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("high_range_images");
        imageProducer.sendMessage(imageListDetailsDTO);
        return modelMapper.map(addDeviceDTO, HighRange.class);
    }

    @Override
    protected HighRange mapEditedDevice(AddHighRangeDTO addHighRangeDTO) {
        return modelMapper.map(addHighRangeDTO, HighRange.class);
    }

    @Override
    protected HighRangeDetailsDTO toDetailsDTO(HighRange highRange) {
        HighRangeDetailsDTO highRangeDetailsDTO = modelMapper.map(highRange, HighRangeDetailsDTO.class);
        highRangeDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return highRangeDetailsDTO;
    }

    @Override
    protected HighRangeSummaryDTO toSummaryDTO(HighRange highRange) {
        HighRangeSummaryDTO highRangeSummaryDTO = modelMapper.map(highRange, HighRangeSummaryDTO.class);
        highRangeSummaryDTO.setLikes(highRange.getLikes());
        return highRangeSummaryDTO;
    }

    @Override
    protected UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    protected void addLikeToEntity(HighRange entity, UserEntity user) {
        List<UserEntity> userLikes = entity.getUserLikes();
        long userId = user.getId();

        for (UserEntity existingUser : userLikes) {
            if (existingUser.getId() == userId) {
                throw new DeviceAlreadyLikedException(
                        String.format(
                                "User with id %d and with username %s has already liked device %s %s",
                                userId,
                                user.getUsername(),
                                entity.getBrand(),
                                entity.getModel()
                        ));
            }
        }

        userLikes.add(user);
    }

    @Override
    protected Optional<HighRange> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    @Override
    protected String getBrand(AddHighRangeDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    @Override
    protected String getModel(AddHighRangeDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }

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
}