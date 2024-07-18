package com.bg.bassheadsbg.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    public KafkaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "image-hosting-firebase-uploaded")
    public void consume(String message) throws IOException, ExecutionException, InterruptedException {

        ImageCreateResponse imageResponse = objectMapper.readValue(message, ImageCreateResponse.class);


            System.out.println("Received Message: " + message);
            System.out.println("old url " + imageResponse.getOldUrl());
            System.out.println("new link " + imageResponse.getUrl());
            System.out.println();


    }
}
