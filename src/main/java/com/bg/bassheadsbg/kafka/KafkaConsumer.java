package com.bg.bassheadsbg.kafka;

import com.bg.bassheadsbg.service.implementation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final HighRangeServiceImpl highRangeService;
    private final MidRangeServiceImpl midRangeService;
    private final SubwooferServiceImpl subwooferService;
    private final MonoAmpServiceImpl monoAmpService;
    private final MultiChannelAmpServiceImpl multiChannelAmpService;

    public KafkaConsumer(ObjectMapper objectMapper,
                         HighRangeServiceImpl highRangeService,
                         MidRangeServiceImpl midRangeService,
                         SubwooferServiceImpl subwooferService,
                         MonoAmpServiceImpl monoAmpService,
                         MultiChannelAmpServiceImpl multiChannelAmpService) {
        this.objectMapper = objectMapper;
        this.highRangeService = highRangeService;
        this.midRangeService = midRangeService;
        this.subwooferService = subwooferService;
        this.monoAmpService = monoAmpService;
        this.multiChannelAmpService = multiChannelAmpService;
    }

    @KafkaListener(topics = "image-hosting-firebase-uploaded")
    public void consume(String message) throws IOException {

        ImageCreateResponse imageResponse = objectMapper.readValue(message, ImageCreateResponse.class);


        log.info("Received Message: {}", message);
        log.info("old url: {}", imageResponse.getOldUrl());
        log.info("new link: {}", imageResponse.getUrl());
        log.info("table name: {}", imageResponse.getTableName());

        switch (imageResponse.getTableName()) {
            case "high_range_images":
                highRangeService.updateDeviceImageUrls(imageResponse.getOldUrl(), imageResponse.getUrl());
                break;
            case "mid_range_images":
                midRangeService.updateDeviceImageUrls(imageResponse.getOldUrl(), imageResponse.getUrl());
                break;
            case "mono_amplifier_images":
                monoAmpService.updateDeviceImageUrls(imageResponse.getOldUrl(), imageResponse.getUrl());
                break;
            case "multi_channel_amplifier_images":
                multiChannelAmpService.updateDeviceImageUrls(imageResponse.getOldUrl(), imageResponse.getUrl());
                break;
            case "subwoofer_images":
                subwooferService.updateDeviceImageUrls(imageResponse.getOldUrl(), imageResponse.getUrl());
                break;
        }
    }
}
