package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.images.MonoAmplifierImage;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;
import com.bg.bassheadsbg.repository.DeviceRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MonoAmpServiceImpl extends DeviceServiceImpl<
        AddMonoAmpDTO,
        MonoAmpDetailsDTO,
        MonoAmpSummaryDTO,
        MonoAmpDetailsHelperDTO,
        MonoAmplifierImage,
        MonoAmplifier> implements MonoAmpService {
    public MonoAmpServiceImpl(DeviceRepository<MonoAmplifier> deviceRepository,
                              ModelMapper modelMapper,
                              ExRateService exRateService,
                              UserService userService,
                              MessageSource messageSource) {
        super(deviceRepository,
                modelMapper,
                exRateService,
                userService,
                messageSource,
                AddMonoAmpDTO::new,
                MonoAmplifier.class,
                MonoAmpDetailsDTO.class,
                MonoAmpSummaryDTO.class,
                MonoAmpDetailsHelperDTO::new,
                MonoAmplifierImage::new);
    }
}