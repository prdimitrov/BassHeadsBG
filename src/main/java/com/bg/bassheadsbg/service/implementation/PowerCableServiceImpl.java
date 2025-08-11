package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.add.AddPowerCableDTO;
import com.bg.bassheadsbg.model.dto.details.PowerCableDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.PowerCableSummaryDTO;
import com.bg.bassheadsbg.model.entity.cables.PowerCable;
import com.bg.bassheadsbg.model.entity.images.PowerCableImage;
import com.bg.bassheadsbg.model.helpers.PowerCableDetailsHelperDTO;
import com.bg.bassheadsbg.repository.DeviceRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.PowerCableService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class PowerCableServiceImpl extends DeviceServiceImpl<
        AddPowerCableDTO,
        PowerCableDetailsDTO,
        PowerCableSummaryDTO,
        PowerCableDetailsHelperDTO,
        PowerCableImage,
        PowerCable> implements PowerCableService {

    public PowerCableServiceImpl(DeviceRepository<PowerCable> deviceRepository,
                                 ModelMapper modelMapper,
                                 ExRateService exRateService,
                                 UserService userService,
                                 MessageSource messageSource) {
        super(deviceRepository,
                modelMapper,
                exRateService,
                userService,
                messageSource,
                AddPowerCableDTO::new,
                PowerCable.class,
                PowerCableDetailsDTO.class,
                PowerCableSummaryDTO.class,
                PowerCableDetailsHelperDTO::new,
                PowerCableImage::new);
    }
}
