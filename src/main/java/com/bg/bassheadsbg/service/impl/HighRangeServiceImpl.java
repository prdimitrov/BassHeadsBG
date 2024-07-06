package com.bg.bassheadsbg.service.impl;

import com.bg.bassheadsbg.model.dto.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.repository.HighRangeRepository;
import com.bg.bassheadsbg.service.HighRangeService;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HighRangeServiceImpl implements HighRangeService {
    private final HighRangeRepository highRangeRepository;
    private final ModelMapper modelMapper;

    public HighRangeServiceImpl(HighRangeRepository highRangeRepository, ModelMapper modelMapper) {
        this.highRangeRepository = highRangeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public long addHighRange(AddHighRangeDTO addHighRangeDTO) {
        return highRangeRepository.save(mapHighRange(addHighRangeDTO)).getId();
    }

    @Override
    public void deleteHighRange(long highRangeId) {
        highRangeRepository.deleteById(highRangeId);
    }

    @Override
    public HighRangeDetailsDTO getHighRangeDetails(Long id) {
        return this.highRangeRepository
                .findById(id)
                .map(this::toHighRangeDetails)
                .orElseThrow();
    }

    @Override
    public List<HighRangeSummaryDTO> getAllHighRangeSummary() {
        return highRangeRepository
                .findAll()
                .stream()
                .map(this::toHighRangeSummary)
                .toList();
    }

    private HighRangeSummaryDTO toHighRangeSummary(HighRange highRange) {
        return modelMapper.map(highRange, HighRangeSummaryDTO.class);
    }

    private HighRangeDetailsDTO toHighRangeDetails(HighRange highRange) {
        return modelMapper.map(highRange, HighRangeDetailsDTO.class);
    }

    private HighRange mapHighRange(AddHighRangeDTO addHighRangeDTO) {
        HighRange mappedHighRange = modelMapper.map(addHighRangeDTO, HighRange.class);

        Optional<HighRange> optHighRange = highRangeRepository.findByBrandAndModel(addHighRangeDTO.getBrand(), addHighRangeDTO.getModel());

        if (optHighRange.isPresent()) {
            throw new EntityExistsException("Speaker with brand "
                    + addHighRangeDTO.getBrand()
                    + " and model "
                    + addHighRangeDTO.getModel()
                    + " already exists.");
        }
        return mappedHighRange;
    }
}
