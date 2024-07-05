package com.bg.bassheadsbg.service.impl;

import com.bg.bassheadsbg.model.dto.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.repository.SubwooferRepository;
import com.bg.bassheadsbg.service.SubwooferService;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubwooferServiceImpl implements SubwooferService {
    private final SubwooferRepository subwooferRepository;
    private final ModelMapper modelMapper;

    public SubwooferServiceImpl(SubwooferRepository subwooferRepository, ModelMapper modelMapper) {
        this.subwooferRepository = subwooferRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public long addSubwoofer(AddSubwooferDTO addSubwooferDTO) {
        return subwooferRepository.save(mapSubwoofer(addSubwooferDTO)).getId();
    }

    @Override
    public void deleteSubwoofer(long subwooferId) {
        subwooferRepository.deleteById(subwooferId);
    }

    @Override
    public SubwooferDetailsDTO getSubwooferDetails(Long id) {
        return this.subwooferRepository
                .findById(id)
                .map(this::toSubwooferDetails)
                .orElseThrow();
    }

    @Override
    public List<SubwooferSummaryDTO> getAllSubwooferSummary() {
        return subwooferRepository
                .findAll()
                .stream()
                .map(this::toSubwooferSummary)
                .toList();
    }

    private SubwooferSummaryDTO toSubwooferSummary(Subwoofer subwoofer) {
        return modelMapper.map(subwoofer, SubwooferSummaryDTO.class);
    }

    private SubwooferDetailsDTO toSubwooferDetails(Subwoofer subwoofer) {
        return modelMapper.map(subwoofer, SubwooferDetailsDTO.class);
    }

    private Subwoofer mapSubwoofer(AddSubwooferDTO addSubwooferDTO) {
        Subwoofer mappedSubwoofer = modelMapper.map(addSubwooferDTO, Subwoofer.class);

        Optional<Subwoofer> optSubwoofer = subwooferRepository.findByBrandAndModel(addSubwooferDTO.getBrand(), addSubwooferDTO.getModel());

        if (optSubwoofer.isPresent()) {
            throw new EntityExistsException("Subwoofer with brand "
                    + addSubwooferDTO.getBrand()
                    + " and model "
                    + addSubwooferDTO.getModel()
                    + " already exists.");
        }
        return mappedSubwoofer;
    }
}
