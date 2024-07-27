package com.bg.bassheadsbg.kafka;

import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageProducer {
    private static final String TOPIC = "image-hosting";
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ImageProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(ImageListDetailsDTO imageListDetailsDTO) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(imageListDetailsDTO);
        kafkaTemplate.send(TOPIC, message);
    }
}
