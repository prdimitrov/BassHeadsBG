package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.repository.HighRangeRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HighRangeServiceImpl extends CommonDeviceServiceImpl<AddHighRangeDTO, HighRangeDetailsDTO, HighRangeSummaryDTO, HighRange, HighRangeRepository>
        implements HighRangeService {

    private final ImageProducer imageProducer;
    private final ExRateService exRateService;

    public HighRangeServiceImpl(HighRangeRepository highRangeRepository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService) {
        super(highRangeRepository, modelMapper);
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
    }

    @Override
    protected HighRange mapToDevice(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException {
        // Produce message to Kafka with image URLs
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("high_range_images");
        imageProducer.sendMessage(imageListDetailsDTO);

        // Map the DTO to HighRange entity
        return modelMapper.map(addDeviceDTO, HighRange.class);
    }

    @Override
    protected HighRange mapEditedDevice(AddHighRangeDTO addHighRangeDTO) {
        // Map the edited DTO to HighRange entity
        return modelMapper.map(addHighRangeDTO, HighRange.class);
    }

    @Override
    protected HighRangeDetailsDTO toDetailsDTO(HighRange highRange) {
        // Map the HighRange entity to DetailsDTO
        HighRangeDetailsDTO highRangeDetailsDTO = modelMapper.map(highRange, HighRangeDetailsDTO.class);
        highRangeDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return highRangeDetailsDTO;
    }

    @Override
    protected HighRangeSummaryDTO toSummaryDTO(HighRange highRange) {
        // Map the HighRange entity to SummaryDTO
        return modelMapper.map(highRange, HighRangeSummaryDTO.class);
    }

    @Override
    protected Optional<HighRange> findByBrandAndModel(String brand, String model) {
        // Find HighRange entity by brand and model
        return repository.findByBrandAndModel(brand, model);
    }

    @Override
    protected String getBrand(AddHighRangeDTO addDeviceDTO) {
        // Extract brand from AddHighRangeDTO
        return addDeviceDTO.getBrand();
    }

    @Override
    protected String getModel(AddHighRangeDTO addDeviceDTO) {
        // Extract model from AddHighRangeDTO
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
}