package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.AddSubwooferDTO;
import com.bg.bassheadsbg.model.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.repository.SubwooferRepository;
import com.bg.bassheadsbg.service.interfaces.SubwooferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubwooferServiceImpl extends CommonDeviceServiceImpl<AddSubwooferDTO, SubwooferDetailsDTO, SubwooferSummaryDTO, Subwoofer, SubwooferRepository>
        implements SubwooferService {

    public SubwooferServiceImpl(SubwooferRepository subwooferRepository, ModelMapper modelMapper) {
        super(subwooferRepository, modelMapper);
    }

//    @Override
//    public SubwooferDetailsDTO getDeviceDetails(Long id) {
//        return repository.findById(id)
//                .map(this::toDetailsDTO)
//                .orElseThrow(() -> new DeviceNotFoundException("Speaker with id " + id + " not found!", id));
//    }

    @Override
    public Subwoofer addDevice(AddSubwooferDTO addDeviceDTO) {
        Subwoofer subwoofer = modelMapper.map(addDeviceDTO, Subwoofer.class);
        return subwoofer;
    }

    @Override
    protected Subwoofer mapToDevice(AddSubwooferDTO addDeviceDTO) {
        Subwoofer subwoofer = modelMapper.map(addDeviceDTO, Subwoofer.class);
        checkEntityExists(addDeviceDTO, addDeviceDTO.getBrand(), addDeviceDTO.getModel());
        return subwoofer;
    }

    @Override
    protected Subwoofer mapEditedDevice(AddSubwooferDTO addSubwooferDTO) {
        return modelMapper.map(addSubwooferDTO, Subwoofer.class);
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