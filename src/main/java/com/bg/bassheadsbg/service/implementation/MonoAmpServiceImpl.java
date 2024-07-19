package com.bg.bassheadsbg.service.implementation;


import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonoAmpServiceImpl extends CommonDeviceServiceImpl<AddMonoAmpDTO, MonoAmpDetailsDTO, MonoAmpSummaryDTO, MonoAmplifier, MonoAmplifierRepository>
        implements MonoAmpService {

    public MonoAmpServiceImpl(MonoAmplifierRepository monoAmplifierRepository, ModelMapper modelMapper) {
        super(monoAmplifierRepository, modelMapper);
    }

//    @Override
//    public MonoAmpDetailsDTO getDeviceDetails(Long id) {
//        return repository.findById(id)
//                .map(this::toDetailsDTO)
//                .orElseThrow(() -> new DeviceNotFoundException("Amp with id " + id + " not found!", id));
//    }

    @Override
    public MonoAmplifier addDevice(AddMonoAmpDTO addMonoAmpDTO) {
        MonoAmplifier monoAmplifier = modelMapper.map(addMonoAmpDTO, MonoAmplifier.class);
        return monoAmplifier;
    }

    @Override
    protected MonoAmplifier mapToDevice(AddMonoAmpDTO addDeviceDTO) {
        MonoAmplifier monoAmplifier = modelMapper.map(addDeviceDTO, MonoAmplifier.class);
        checkEntityExists(addDeviceDTO, addDeviceDTO.getBrand(), addDeviceDTO.getModel());
        return monoAmplifier;
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
}