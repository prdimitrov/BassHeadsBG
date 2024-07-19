package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.repository.HighRangeRepository;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HighRangeServiceImpl extends CommonDeviceServiceImpl<AddHighRangeDTO, HighRangeDetailsDTO, HighRangeSummaryDTO, HighRange, HighRangeRepository>
        implements HighRangeService {

    public HighRangeServiceImpl(HighRangeRepository highRangeRepository, ModelMapper modelMapper) {
        super(highRangeRepository, modelMapper);
    }

//    @Override
//    public HighRangeDetailsDTO getDeviceDetails(Long id) {
//        return repository.findById(id)
//                .map(this::toDetailsDTO)
//                .orElseThrow(() -> new DeviceNotFoundException("Speaker with id " + id + " not found!", id));
//    }


    @Override
    public HighRange addDevice(AddHighRangeDTO addDeviceDTO) {
        HighRange highRange = modelMapper.map(addDeviceDTO, HighRange.class);
        return highRange;
    }

    @Override
    protected HighRange mapToDevice(AddHighRangeDTO addDeviceDTO) {
        HighRange highRange = modelMapper.map(addDeviceDTO, HighRange.class);
        checkEntityExists(addDeviceDTO, addDeviceDTO.getBrand(), addDeviceDTO.getModel());
        return highRange;
    }

    @Override
    protected HighRange mapEditedDevice(AddHighRangeDTO addHighRangeDTO) {
        return modelMapper.map(addHighRangeDTO, HighRange.class);
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