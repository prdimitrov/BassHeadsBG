package com.bg.bassheadsbg.kafka;

import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ImageProducer imageProducer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() throws JsonProcessingException {
        ImageListDetailsDTO dto = new ImageListDetailsDTO();
        dto.setTableName("testTable");
        dto.setImageUrls(List.of("http://example.com/image1.jpg", "http://example.com/image2.jpg"));

        String expectedMessage = "{\"tableName\":\"testTable\",\"imageUrls\":[\"http://example.com/image1.jpg\",\"http://example.com/image2.jpg\"]}";
        when(objectMapper.writeValueAsString(dto)).thenReturn(expectedMessage);

        imageProducer.sendMessage(dto);

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(eq("image-hosting"), messageCaptor.capture());

        assertEquals(expectedMessage, messageCaptor.getValue());
    }
}
