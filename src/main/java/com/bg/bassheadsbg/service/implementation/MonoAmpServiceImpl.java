package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonoAmpServiceImpl extends CommonDeviceServiceImpl<AddMonoAmpDTO, MonoAmpDetailsDTO, MonoAmpSummaryDTO, MonoAmplifier, MonoAmplifierRepository>
        implements MonoAmpService {

    private final ImageProducer imageProducer;

    public MonoAmpServiceImpl(MonoAmplifierRepository monoAmplifierRepository, ModelMapper modelMapper, ImageProducer imageProducer) {
        super(monoAmplifierRepository, modelMapper);
        this.imageProducer = imageProducer;
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
        return modelMapper.map(monoAmplifier, MonoAmpDetailsDTO.class);
    }

    @Override
    protected MonoAmpSummaryDTO toSummaryDTO(MonoAmplifier monoAmplifier) {
        return modelMapper.map(monoAmplifier, MonoAmpSummaryDTO.class);
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