package com.bg.bassheadsbg.service.impl;

import com.bg.bassheadsbg.model.dto.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.repository.HighRangeRepository;
import com.bg.bassheadsbg.service.HighRangeService;
import com.bg.bassheadsbg.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HighRangeServiceImpl extends CommonDeviceServiceImpl<AddHighRangeDTO, HighRangeDetailsDTO, HighRangeSummaryDTO, HighRange, HighRangeRepository>
        implements HighRangeService {

    public HighRangeServiceImpl(HighRangeRepository highRangeRepository, ModelMapper modelMapper) {
        super(highRangeRepository, modelMapper);
    }

    @Override
    public HighRangeDetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new ObjectNotFoundException("Speaker with id " + id + " not found!", id));
                                                              //TODO: DO THE .orELseThrow for the other services!!!!
                                                              //TODO: OR FIND A WAY TO DO IT IN THE CommonDeviceServiceImpl!!!!
    }

    @Override
    protected HighRange mapToDevice(AddHighRangeDTO addHighRangeDTO) {
        HighRange highRange = modelMapper.map(addHighRangeDTO, HighRange.class);
        checkEntityExists(addHighRangeDTO, addHighRangeDTO.getBrand(), addHighRangeDTO.getModel());
        return highRange;
    }

    @Override
    protected HighRangeDetailsDTO toDetailsDTO(HighRange highRange) {
        return modelMapper.map(highRange, HighRangeDetailsDTO.class);
    }

    @Override
    protected HighRangeSummaryDTO toSummaryDTO(HighRange highRange) {
        return modelMapper.map(highRange, HighRangeSummaryDTO.class);
    }

    @Override
    protected Optional<HighRange> findByBrandAndModel(String brand, String model) {
        return repository.findByBrandAndModel(brand, model);
    }
}