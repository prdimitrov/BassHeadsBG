package com.bg.bassheadsbg.service.impl;

import com.bg.bassheadsbg.model.dto.*;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.repository.MidRangeRepository;
import com.bg.bassheadsbg.service.MidRangeService;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MidRangeServiceImpl implements MidRangeService {
    private final MidRangeRepository midRangeRepository;
    private final ModelMapper modelMapper;

    public MidRangeServiceImpl(MidRangeRepository midRangeRepository, ModelMapper modelMapper) {
        this.midRangeRepository = midRangeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public long addMidRange(AddMidRangeDTO addMidRangeDTO) {
        return midRangeRepository.save(mapMidRange(addMidRangeDTO)).getId();
    }

    @Override
    public void deleteMidRange(long midRangeId) {
        midRangeRepository.deleteById(midRangeId);
    }

    @Override
    public MidRangeDetailsDTO getMidRangeDetails(Long id) {
        return this.midRangeRepository
                .findById(id)
                .map(this::toMidRangeDetails)
                .orElseThrow();
    }

    @Override
    public List<MidRangeSummaryDTO> getAllMidRangeSummary() {
        return midRangeRepository
                .findAll()
                .stream()
                .map(this::toMidRangeSummary)
                .toList();
    }

    private MidRangeSummaryDTO toMidRangeSummary(MidRange midRange) {
        return modelMapper.map(midRange, MidRangeSummaryDTO.class);
    }

    private MidRangeDetailsDTO toMidRangeDetails(MidRange midRange) {
        return modelMapper.map(midRange, MidRangeDetailsDTO.class);
    }

    private MidRange mapMidRange(AddMidRangeDTO addMidRangeDTO) {
        MidRange mappedMidRange = modelMapper.map(addMidRangeDTO, MidRange.class);

        Optional<MidRange> optMidRange = midRangeRepository.findByBrandAndModel(addMidRangeDTO.getBrand(), addMidRangeDTO.getModel());

        if (optMidRange.isPresent()) {
            throw new EntityExistsException("Speaker with brand "
                    + addMidRangeDTO.getBrand()
                    + " and model "
                    + addMidRangeDTO.getModel()
                    + " already exists.");
        }
        return mappedMidRange;
    }
}
