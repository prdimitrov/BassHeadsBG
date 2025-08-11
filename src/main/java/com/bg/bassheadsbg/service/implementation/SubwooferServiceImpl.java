package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.entity.images.SubwooferImage;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.model.helpers.SubwooferDetailsHelperDTO;
import com.bg.bassheadsbg.repository.DeviceRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.SubwooferService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class SubwooferServiceImpl extends DeviceServiceImpl<
        AddSubwooferDTO,
        SubwooferDetailsDTO,
        SubwooferSummaryDTO,
        SubwooferDetailsHelperDTO,
        SubwooferImage,
        Subwoofer>
        implements SubwooferService {
    public SubwooferServiceImpl(DeviceRepository<Subwoofer> deviceRepository,
                                ModelMapper modelMapper,
                                ExRateService exRateService,
                                UserService userService,
                                MessageSource messageSource) {
        super(deviceRepository,
                modelMapper,
                exRateService,
                userService,
                messageSource,
                AddSubwooferDTO::new,
                Subwoofer.class,
                SubwooferDetailsDTO.class,
                SubwooferSummaryDTO.class,
                SubwooferDetailsHelperDTO::new,
                SubwooferImage::new);
    }
}