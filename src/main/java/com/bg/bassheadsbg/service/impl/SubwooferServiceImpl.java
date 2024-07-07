package com.bg.bassheadsbg.service.impl;

import com.bg.bassheadsbg.model.dto.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.repository.SubwooferRepository;
import com.bg.bassheadsbg.service.SubwooferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubwooferServiceImpl extends CommonDeviceServiceImpl<AddSubwooferDTO, SubwooferDetailsDTO, SubwooferSummaryDTO, Subwoofer, SubwooferRepository>
        implements SubwooferService {

    public SubwooferServiceImpl(SubwooferRepository subwooferRepository, ModelMapper modelMapper) {
        super(subwooferRepository, modelMapper);
    }

    @Override
    protected Subwoofer mapToDevice(AddSubwooferDTO addSubwooferDTO) {
        Subwoofer subwoofer = modelMapper.map(addSubwooferDTO, Subwoofer.class);
        checkEntityExists(addSubwooferDTO, addSubwooferDTO.getBrand(), addSubwooferDTO.getModel());
        return subwoofer;
    }

    @Override
    protected SubwooferDetailsDTO toDetailsDTO(Subwoofer subwoofer) {
        return modelMapper.map(subwoofer, SubwooferDetailsDTO.class);
    }

    @Override
    protected SubwooferSummaryDTO toSummaryDTO(Subwoofer subwoofer) {
        return modelMapper.map(subwoofer, SubwooferSummaryDTO.class);
    }

    @Override
    protected Optional<Subwoofer> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }
}