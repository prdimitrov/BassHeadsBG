package com.bg.bassheadsbg.service.impl;


import com.bg.bassheadsbg.model.dto.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.repository.MultiChannelAmplifierRepository;
import com.bg.bassheadsbg.service.MultiChannelAmpService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MultiChannelAmpServiceImpl extends CommonDeviceServiceImpl<AddMultiChannelAmpDTO, MultiChannelAmpDetailsDTO, MultiChannelAmpSummaryDTO, MultiChannelAmplifier, MultiChannelAmplifierRepository>
        implements MultiChannelAmpService {

    public MultiChannelAmpServiceImpl(MultiChannelAmplifierRepository multiChannelAmplifierRepository, ModelMapper modelMapper) {
        super(multiChannelAmplifierRepository, modelMapper);
    }

    @Override
    protected MultiChannelAmplifier mapToDevice(AddMultiChannelAmpDTO addMultiChannelAmpDTO) {
        MultiChannelAmplifier multiChannelAmplifier = modelMapper.map(addMultiChannelAmpDTO, MultiChannelAmplifier.class);
        checkEntityExists(addMultiChannelAmpDTO, addMultiChannelAmpDTO.getBrand(), addMultiChannelAmpDTO.getModel());
        return multiChannelAmplifier;
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
}