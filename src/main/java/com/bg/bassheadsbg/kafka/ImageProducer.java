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

    /**
     * This method is used to send a message to the Kafka topic specified by the TOPIC constant.
     * The message contains details about a list of images, encapsulated in an ImageListDetailsDTO object.
     *
     * @param imageListDetailsDTO - accepts an ImageListDetailsDTO object containing:
     *                           {String tableName} - the name of the table associated with the images
     *                           {List<String> imageUrls} - a list of URLs for the images
     * @throws JsonProcessingException - if an error occurs while converting the ImageListDetailsDTO object to JSON format
     */
    public void sendMessage(ImageListDetailsDTO imageListDetailsDTO) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(imageListDetailsDTO);
        kafkaTemplate.send(TOPIC, message);
    }
}
