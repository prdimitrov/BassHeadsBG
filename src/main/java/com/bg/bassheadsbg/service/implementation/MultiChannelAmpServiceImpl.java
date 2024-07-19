package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.kafka.ImageListDTO;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.repository.MultiChannelAmplifierRepository;
import com.bg.bassheadsbg.service.interfaces.MultiChannelAmpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultiChannelAmpServiceImpl extends CommonDeviceServiceImpl<AddMultiChannelAmpDTO, MultiChannelAmpDetailsDTO, MultiChannelAmpSummaryDTO, MultiChannelAmplifier, MultiChannelAmplifierRepository>
        implements MultiChannelAmpService {

    private final ImageProducer imageProducer;

    public MultiChannelAmpServiceImpl(MultiChannelAmplifierRepository multiChannelAmplifierRepository, ModelMapper modelMapper, ImageProducer imageProducer) {
        super(multiChannelAmplifierRepository, modelMapper);
        this.imageProducer = imageProducer;
    }

    @Override
    protected MultiChannelAmplifier mapToDevice(AddMultiChannelAmpDTO addDeviceDTO) throws JsonProcessingException {
        ImageListDTO imageListDTO = new ImageListDTO();
        imageListDTO.setImageUrls(addDeviceDTO.getImages());
        imageListDTO.setTableName("multi_channel_amplifier_images");
        imageProducer.sendMessage(imageListDTO);
        return modelMapper.map(addDeviceDTO, MultiChannelAmplifier.class);
    }

    @Override
    protected MultiChannelAmplifier mapEditedDevice(AddMultiChannelAmpDTO addMultiChannelAmpDTO) {
        return modelMapper.map(addMultiChannelAmpDTO, MultiChannelAmplifier.class);
    }

    @Override
    protected MultiChannelAmpDetailsDTO toDetailsDTO(MultiChannelAmplifier multiChannelAmplifier) {
        return modelMapper.map(multiChannelAmplifier, MultiChannelAmpDetailsDTO.class);
    }

    @Override
    protected MultiChannelAmpSummaryDTO toSummaryDTO(MultiChannelAmplifier multiChannelAmplifier) {
        return modelMapper.map(multiChannelAmplifier, MultiChannelAmpSummaryDTO.class);
    }

    @Override
    protected Optional<MultiChannelAmplifier> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }

    @Override
    protected String getBrand(AddMultiChannelAmpDTO addDeviceDTO) {
        return addDeviceDTO.getBrand();
    }

    @Override
    protected String getModel(AddMultiChannelAmpDTO addDeviceDTO) {
        return addDeviceDTO.getModel();
    }

    @Override
    public void updateDeviceImageUrls(String oldUrl, String newUrl) {
        List<MultiChannelAmplifier> multiChannelAmplifiers = repository.findByImagesContaining(oldUrl);

        for (MultiChannelAmplifier multiChannelAmplifier : multiChannelAmplifiers) {
            List<String> images = multiChannelAmplifier.getImages();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).equals(oldUrl)) {
                    images.set(i, newUrl);
                }
            }
            multiChannelAmplifier.setImages(images);
            repository.save(multiChannelAmplifier);
        }
    }
}