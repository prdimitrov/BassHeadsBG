package com.bg.bassheadsbg.service.impl;


import com.bg.bassheadsbg.model.dto.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.service.MonoAmpService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonoAmpServiceImpl extends CommonDeviceServiceImpl<AddMonoAmpDTO, MonoAmpDetailsDTO, MonoAmpSummaryDTO, MonoAmplifier, MonoAmplifierRepository>
        implements MonoAmpService {

    public MonoAmpServiceImpl(MonoAmplifierRepository monoAmplifierRepository, ModelMapper modelMapper) {
        super(monoAmplifierRepository, modelMapper);
    }

    @Override
    protected MonoAmplifier mapToDevice(AddMonoAmpDTO addMonoAmpDTO) {
        MonoAmplifier monoAmplifier = modelMapper.map(addMonoAmpDTO, MonoAmplifier.class);
        checkEntityExists(addMonoAmpDTO, addMonoAmpDTO.getBrand(), addMonoAmpDTO.getModel());
        return monoAmplifier;
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