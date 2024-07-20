package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.service.interfaces.CommonDeviceService;
import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class CommonDeviceServiceImpl<AddDTO, DetailsDTO, SummaryDTO, Entity extends BaseEntity, Repo extends JpaRepository<Entity, Long>>
        implements CommonDeviceService<AddDTO, DetailsDTO, SummaryDTO> {

    protected final Repo repository;
    protected final ModelMapper modelMapper;

    protected CommonDeviceServiceImpl(Repo repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long addDevice(AddDTO addDeviceDTO) throws JsonProcessingException {
        checkEntityExists(addDeviceDTO, getBrand(addDeviceDTO), getModel(addDeviceDTO));
        Entity entity = mapToDevice(addDeviceDTO);
        return repository.save(entity).getId();
    }

    @Override
    public long editDevice(AddDTO addDeviceDTO) {
        Entity entity = mapEditedDevice(addDeviceDTO);
        return repository.save(entity).getId();
    }

    @Override
    public void deleteDevice(long deviceId) {
        repository.deleteById(deviceId);
    }

    @Override
    public DetailsDTO getDeviceDetails(Long id) {
        return repository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new DeviceNotFoundException(ExceptionMessages.DEVICE_NOT_FOUND, id));
    }

    @Override
    public List<SummaryDTO> getAllDeviceSummary() {
        return repository.findAll()
                .stream()
                .map(this::toSummaryDTO)
                .toList();
    }

    protected abstract Entity mapToDevice(AddDTO addDeviceDTO) throws JsonProcessingException;

    protected abstract Entity mapEditedDevice(AddDTO addDeviceDTO);

    protected abstract DetailsDTO toDetailsDTO(Entity entity);

    protected abstract SummaryDTO toSummaryDTO(Entity entity);

    public void likeDevice(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        UserEntity user = null;
        if (principal instanceof UserDetails userDetails) {
            user = getUserEntity(userDetails.getUsername());
        } else {
            throw new RuntimeException("User not authenticated!");
        }

        Optional<Entity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            Entity entity = optionalEntity.get();
            addLikeToEntity(entity, user);
            repository.save(entity);
        } else {
            throw new DeviceNotFoundException("Device with id " + id + " not found!", id);
        }
    }

    protected abstract UserEntity getUserEntity(String username);

    protected abstract void addLikeToEntity(Entity entity, UserEntity user);

    protected void checkEntityExists(AddDTO addDeviceDTO, String brand, String model) {
        Optional<Entity> existingEntity = findByBrandAndModel(brand, model);
        if (existingEntity.isPresent()) {
            throw new EntityExistsException("Device with brand " + brand + " and model " + model + " already exists.");
        }
    }

    protected abstract Optional<Entity> findByBrandAndModel(String brand, String model);

    protected abstract String getBrand(AddDTO addDeviceDTO);

    protected abstract String getModel(AddDTO addDeviceDTO);

    protected abstract void updateDeviceImageUrls(String oldUrl, String newUrl);
}