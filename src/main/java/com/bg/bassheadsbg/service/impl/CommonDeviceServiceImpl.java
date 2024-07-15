package com.bg.bassheadsbg.service.impl;

import com.bg.bassheadsbg.service.CommonDeviceService;
import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class CommonDeviceServiceImpl<AddDTO, DetailsDTO, SummaryDTO, Entity extends BaseEntity, Repo extends JpaRepository<Entity, Long>>
        implements CommonDeviceService<AddDTO, DetailsDTO, SummaryDTO> {

    protected final Repo repository;
    protected final ModelMapper modelMapper;

    protected CommonDeviceServiceImpl(Repo repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long addDevice(AddDTO addDeviceDTO) {
        return repository.save(mapToDevice(addDeviceDTO)).getId();
    }

    @Override
    public void deleteDevice(long deviceId) {
        repository.deleteById(deviceId);
    }

    @Override
    public DetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow();
    }

    @Override
    public List<SummaryDTO> getAllDeviceSummary() {
        return repository.findAll()
                .stream()
                .map(this::toSummaryDTO)
                .toList();
    }

    protected abstract Entity mapToDevice(AddDTO addDeviceDTO);

    protected abstract DetailsDTO toDetailsDTO(Entity entity);

    protected abstract SummaryDTO toSummaryDTO(Entity entity);

    protected void checkEntityExists(AddDTO addDeviceDTO, String brand, String model) {
        Optional<Entity> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            throw new EntityExistsException("Device with brand " + brand + " and model " + model + " already exists.");
        }
    }

    protected abstract Optional<Entity> findByBrandAndModel(String brand, String model);
}