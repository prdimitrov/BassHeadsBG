package com.bg.bassheadsbg.kafka;

import com.bg.bassheadsbg.service.implementation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KafkaConsumerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HighRangeServiceImpl highRangeService;

    @Mock
    private MidRangeServiceImpl midRangeService;

    @Mock
    private SubwooferServiceImpl subwooferService;

    @Mock
    private MonoAmpServiceImpl monoAmpService;

    @Mock
    private MultiChannelAmpServiceImpl multiChannelAmpService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsume_highRangeImages() throws IOException {
        ImageCreateResponse imageResponse = new ImageCreateResponse();
        imageResponse.setId("1");
        imageResponse.setUrl("http://example.com/newImage.jpg");
        imageResponse.setOldUrl("http://example.com/oldImage.jpg");
        imageResponse.setTableName("high_range_images");

        String message = "{\"id\":\"1\",\"url\":\"http://example.com/newImage.jpg\",\"oldUrl\":\"http://example.com/oldImage.jpg\",\"tableName\":\"high_range_images\"}";

        when(objectMapper.readValue(message, ImageCreateResponse.class)).thenReturn(imageResponse);

        kafkaConsumer.consume(message);

        verify(highRangeService).updateDeviceImageUrls(eq("http://example.com/oldImage.jpg"), eq("http://example.com/newImage.jpg"));
    }

    @Test
    public void testConsume_midRangeImages() throws IOException {
        ImageCreateResponse imageResponse = new ImageCreateResponse();
        imageResponse.setId("2");
        imageResponse.setUrl("http://example.com/newImage2.jpg");
        imageResponse.setOldUrl("http://example.com/oldImage2.jpg");
        imageResponse.setTableName("mid_range_images");

        String message = "{\"id\":\"2\",\"url\":\"http://example.com/newImage2.jpg\",\"oldUrl\":\"http://example.com/oldImage2.jpg\",\"tableName\":\"mid_range_images\"}";

        when(objectMapper.readValue(message, ImageCreateResponse.class)).thenReturn(imageResponse);

        kafkaConsumer.consume(message);

        verify(midRangeService).updateDeviceImageUrls(eq("http://example.com/oldImage2.jpg"), eq("http://example.com/newImage2.jpg"));
    }

    @Test
    public void testConsume_monoAmpImages() throws IOException {
        ImageCreateResponse imageResponse = new ImageCreateResponse();
        imageResponse.setId("3");
        imageResponse.setUrl("http://example.com/newImage3.jpg");
        imageResponse.setOldUrl("http://example.com/oldImage3.jpg");
        imageResponse.setTableName("mono_amplifier_images");

        String message = "{\"id\":\"3\",\"url\":\"http://example.com/newImage3.jpg\",\"oldUrl\":\"http://example.com/oldImage3.jpg\",\"tableName\":\"mono_amplifier_images\"}";

        when(objectMapper.readValue(message, ImageCreateResponse.class)).thenReturn(imageResponse);

        kafkaConsumer.consume(message);

        verify(monoAmpService).updateDeviceImageUrls(eq("http://example.com/oldImage3.jpg"), eq("http://example.com/newImage3.jpg"));
    }

    @Test
    public void testConsume_multiChannelAmpImages() throws IOException {
        ImageCreateResponse imageResponse = new ImageCreateResponse();
        imageResponse.setId("4");
        imageResponse.setUrl("http://example.com/newImage4.jpg");
        imageResponse.setOldUrl("http://example.com/oldImage4.jpg");
        imageResponse.setTableName("multi_channel_amplifier_images");

        String message = "{\"id\":\"4\",\"url\":\"http://example.com/newImage4.jpg\",\"oldUrl\":\"http://example.com/oldImage4.jpg\",\"tableName\":\"multi_channel_amplifier_images\"}";

        when(objectMapper.readValue(message, ImageCreateResponse.class)).thenReturn(imageResponse);

        kafkaConsumer.consume(message);

        verify(multiChannelAmpService).updateDeviceImageUrls(eq("http://example.com/oldImage4.jpg"), eq("http://example.com/newImage4.jpg"));
    }

    @Test
    public void testConsume_subwooferImages() throws IOException {
        ImageCreateResponse imageResponse = new ImageCreateResponse();
        imageResponse.setId("5");
        imageResponse.setUrl("http://example.com/newImage5.jpg");
        imageResponse.setOldUrl("http://example.com/oldImage5.jpg");
        imageResponse.setTableName("subwoofer_images");

        String message = "{\"id\":\"5\",\"url\":\"http://example.com/newImage5.jpg\",\"oldUrl\":\"http://example.com/oldImage5.jpg\",\"tableName\":\"subwoofer_images\"}";

        when(objectMapper.readValue(message, ImageCreateResponse.class)).thenReturn(imageResponse);

        kafkaConsumer.consume(message);

        verify(subwooferService).updateDeviceImageUrls(eq("http://example.com/oldImage5.jpg"), eq("http://example.com/newImage5.jpg"));
    }
}
