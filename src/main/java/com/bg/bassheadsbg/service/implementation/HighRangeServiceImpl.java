package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.images.HighRangeImage;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;
import com.bg.bassheadsbg.repository.DeviceImageRepository;
import com.bg.bassheadsbg.repository.DeviceRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing high-range speakers.
 * This class provides methods for creating, editing, deleting, and retrieving high-range speakers,
 * also handling the likes and updating images.
 */

@Service
@Slf4j
public class HighRangeServiceImpl extends DeviceServiceImpl<
        AddHighRangeDTO,
        HighRangeDetailsDTO,
        HighRangeSummaryDTO,
        HighRangeDetailsHelperDTO,
        HighRangeImage,
        HighRange
        > implements HighRangeService {

    public HighRangeServiceImpl(
            DeviceRepository<HighRange> deviceRepository,
            DeviceImageRepository<HighRangeImage, HighRange> deviceImageRepository,
            ModelMapper modelMapper,
            ExRateService exRateService,
            UserService userService,
            MessageSource messageSource
    ) {
        super(
                deviceRepository,
                deviceImageRepository,
                modelMapper,
                exRateService,
                userService,
                messageSource,
                AddHighRangeDTO::new,
                HighRange.class,
                HighRangeDetailsDTO.class,
                HighRangeSummaryDTO.class,
                HighRangeDetailsHelperDTO::new,
                HighRangeImage::new
        );
    }
}