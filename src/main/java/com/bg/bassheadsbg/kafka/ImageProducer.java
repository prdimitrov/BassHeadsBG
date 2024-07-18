package com.bg.bassheadsbg.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageProducer {
    private static final String TOPIC = "image-hosting";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(ImageListDTO imageListDTO) {
        kafkaTemplate.send(TOPIC, imageListDTO.getImageUrls().toString());
    }
}
