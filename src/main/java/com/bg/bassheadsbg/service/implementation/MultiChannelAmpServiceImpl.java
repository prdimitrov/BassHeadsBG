package com.bg.bassheadsbg.service.implementation;


import com.bg.bassheadsbg.model.dto.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.repository.MultiChannelAmplifierRepository;
import com.bg.bassheadsbg.service.interfaces.MultiChannelAmpService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MultiChannelAmpServiceImpl extends CommonDeviceServiceImpl<AddMultiChannelAmpDTO, MultiChannelAmpDetailsDTO, MultiChannelAmpSummaryDTO, MultiChannelAmplifier, MultiChannelAmplifierRepository>
        implements MultiChannelAmpService {

    public MultiChannelAmpServiceImpl(MultiChannelAmplifierRepository multiChannelAmplifierRepository, ModelMapper modelMapper) {
        super(multiChannelAmplifierRepository, modelMapper);
    }

//    @Override
//    public MultiChannelAmpDetailsDTO getDeviceDetails(Long id) {
//        return repository.findById(id)
//                .map(this::toDetailsDTO)
//                .orElseThrow(() -> new DeviceNotFoundException("Amp with id " + id + " not found!", id));
//    }

    @Override
    public MultiChannelAmplifier addDevice(AddMultiChannelAmpDTO addDeviceDTO) {
        MultiChannelAmplifier multiChannelAmplifier = modelMapper.map(addDeviceDTO, MultiChannelAmplifier.class);
        return multiChannelAmplifier;
    }

    @Override
    protected MultiChannelAmplifier mapToDevice(AddMultiChannelAmpDTO addDeviceDTO) {
        MultiChannelAmplifier multiChannelAmplifier = modelMapper.map(addDeviceDTO, MultiChannelAmplifier.class);
        checkEntityExists(addDeviceDTO, addDeviceDTO.getBrand(), addDeviceDTO.getModel());
        return multiChannelAmplifier;
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
}