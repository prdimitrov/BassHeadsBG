package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.repository.MidRangeRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MidRangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MidRangeServiceImpl extends CommonDeviceServiceImpl<AddMidRangeDTO, MidRangeDetailsDTO, MidRangeSummaryDTO, MidRange, MidRangeRepository>
        implements MidRangeService {

    private final ImageProducer imageProducer;
    private final ExRateService exRateService;

    public MidRangeServiceImpl(MidRangeRepository midRangeRepository, ModelMapper modelMapper, ImageProducer imageProducer, ExRateService exRateService) {
        super(midRangeRepository, modelMapper);
        this.imageProducer = imageProducer;
        this.exRateService = exRateService;
    }

    @Override
    protected MidRange mapToDevice(AddMidRangeDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDetailsDTO imageListDetailsDTO = new ImageListDetailsDTO();
        imageListDetailsDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDetailsDTO.setTableName("mid_range_images");
        imageProducer.sendMessage(imageListDetailsDTO);
        return modelMapper.map(addDeviceDTO, MidRange.class);
    }

    @Override
    protected MidRange mapEditedDevice(AddMidRangeDTO addMidRangeDTO) {
        return modelMapper.map(addMidRangeDTO, MidRange.class);
    }

    @Override
    protected MidRangeDetailsDTO toDetailsDTO(MidRange midRange) {
        MidRangeDetailsDTO midRangeDetailsDTO = modelMapper.map(midRange, MidRangeDetailsDTO.class);
        midRangeDetailsDTO.setAllCurrencies(exRateService.allSupportedCurrencies());
        return midRangeDetailsDTO;
    }

    @Override
    protected MidRangeSummaryDTO toSummaryDTO(MidRange midRange) {
        return modelMapper.map(midRange, MidRangeSummaryDTO.class);
    }

    @Override
    protected Optional<MidRange> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    @Override
    protected String getBrand(AddMidRangeDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    @Override
    protected String getModel(AddMidRangeDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }

    @Override
    public void updateDeviceImageUrls(String oldUrl, String newUrl) {
        List<MidRange> midRanges = repository.findByImagesContaining(oldUrl);

        for (MidRange midRange : midRanges) {
            List<String> images = midRange.getImages();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).equals(oldUrl)) {
                    images.set(i, newUrl);
                }
            }
            midRange.setImages(images);
            repository.save(midRange);
        }
    }
}