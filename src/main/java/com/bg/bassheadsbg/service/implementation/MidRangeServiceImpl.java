package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.images.MidRangeImage;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;
import com.bg.bassheadsbg.repository.DeviceRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.MidRangeService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing mid-range speakers.
 * This class provides methods for creating, editing, deleting, and retrieving mid-range speakers,
 * also handling the likes and updating images.
 */
@Service
public class MidRangeServiceImpl extends DeviceServiceImpl<
        AddMidRangeDTO,
        MidRangeDetailsDTO,
        MidRangeSummaryDTO,
        MidRangeDetailsHelperDTO,
        MidRangeImage,
        MidRange> implements MidRangeService {
    public MidRangeServiceImpl(DeviceRepository<MidRange> deviceRepository,
                               ModelMapper modelMapper,
                               ExRateService exRateService,
                               UserService userService,
                               MessageSource messageSource) {
        super(deviceRepository,
                modelMapper,
                exRateService,
                userService,
                messageSource,
                AddMidRangeDTO::new,
                MidRange.class,
                MidRangeDetailsDTO.class,
                MidRangeSummaryDTO.class,
                MidRangeDetailsHelperDTO::new,
                MidRangeImage::new);
    }
}