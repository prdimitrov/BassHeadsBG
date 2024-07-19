package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.AddMidRangeDTO;
import com.bg.bassheadsbg.model.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.repository.MidRangeRepository;
import com.bg.bassheadsbg.service.interfaces.MidRangeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MidRangeServiceImpl extends CommonDeviceServiceImpl<AddMidRangeDTO, MidRangeDetailsDTO, MidRangeSummaryDTO, MidRange, MidRangeRepository>
        implements MidRangeService {

    public MidRangeServiceImpl(MidRangeRepository midRangeRepository, ModelMapper modelMapper) {
        super(midRangeRepository, modelMapper);
    }

//    @Override
//    public MidRangeDetailsDTO getDeviceDetails(Long id) {
//        return repository.findById(id)
//                .map(this::toDetailsDTO)
//                .orElseThrow(() -> new DeviceNotFoundException("Speaker with id " + id + " not found!", id));
//    }

    @Override
    public MidRange addDevice(AddMidRangeDTO addDeviceDTO) {
        MidRange midRange = modelMapper.map(addDeviceDTO, MidRange.class);
        return midRange;
    }

    @Override
    protected MidRange mapToDevice(AddMidRangeDTO addDeviceDTO) {
        MidRange midRange = modelMapper.map(addDeviceDTO, MidRange.class);
        checkEntityExists(addDeviceDTO, addDeviceDTO.getBrand(), addDeviceDTO.getModel());
        return midRange;
    }

    @Override
    protected MidRange mapEditedDevice(AddMidRangeDTO addMidRangeDTO) {
        return modelMapper.map(addMidRangeDTO, MidRange.class);
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