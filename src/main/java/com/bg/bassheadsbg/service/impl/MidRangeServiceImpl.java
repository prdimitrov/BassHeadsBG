package com.bg.bassheadsbg.service.impl;

import com.bg.bassheadsbg.model.dto.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.repository.MidRangeRepository;
import com.bg.bassheadsbg.service.MidRangeService;
import com.bg.bassheadsbg.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MidRangeServiceImpl extends CommonDeviceServiceImpl<AddMidRangeDTO, MidRangeDetailsDTO, MidRangeSummaryDTO, MidRange, MidRangeRepository>
        implements MidRangeService {

    public MidRangeServiceImpl(MidRangeRepository midRangeRepository, ModelMapper modelMapper) {
        super(midRangeRepository, modelMapper);
    }

    @Override
    public MidRangeDetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new ObjectNotFoundException("Speaker with id " + id + " not found!", id));
    }

    @Override
    protected MidRange mapToDevice(AddMidRangeDTO addMidRangeDTO) {
        MidRange midRange = modelMapper.map(addMidRangeDTO, MidRange.class);
        checkEntityExists(addMidRangeDTO, addMidRangeDTO.getBrand(), addMidRangeDTO.getModel());
        return midRange;
    }

    @Override
    protected MidRangeDetailsDTO toDetailsDTO(MidRange midRange) {
        return modelMapper.map(midRange, MidRangeDetailsDTO.class);
    }

    @Override
    protected MidRangeSummaryDTO toSummaryDTO(MidRange midRange) {
        return modelMapper.map(midRange, MidRangeSummaryDTO.class);
    }

    @Override
    protected Optional<MidRange> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }
}