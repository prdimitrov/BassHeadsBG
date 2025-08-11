package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.model.entity.images.MultiChannelAmplifierImage;
import com.bg.bassheadsbg.model.helpers.MultiChannelAmpDetailsHelperDTO;
import com.bg.bassheadsbg.repository.DeviceRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MultiChannelAmpService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MultiChannelAmpServiceImpl extends DeviceServiceImpl<
        AddMultiChannelAmpDTO,
        MultiChannelAmpDetailsDTO,
        MultiChannelAmpSummaryDTO,
        MultiChannelAmpDetailsHelperDTO,
        MultiChannelAmplifierImage,
        MultiChannelAmplifier> implements MultiChannelAmpService {

    public MultiChannelAmpServiceImpl(DeviceRepository<MultiChannelAmplifier> deviceRepository,
                                      ModelMapper modelMapper,
                                      ExRateService exRateService,
                                      UserService userService,
                                      MessageSource messageSource) {
        super(deviceRepository,
                modelMapper,
                exRateService,
                userService,
                messageSource,
                AddMultiChannelAmpDTO::new,
                MultiChannelAmplifier.class,
                MultiChannelAmpDetailsDTO.class,
                MultiChannelAmpSummaryDTO.class,
                MultiChannelAmpDetailsHelperDTO::new,
                MultiChannelAmplifierImage::new);
    }
}