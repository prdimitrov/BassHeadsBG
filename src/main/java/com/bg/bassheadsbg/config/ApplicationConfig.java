package com.bg.bassheadsbg.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {
    private static final String IMAGE_FILES = "imageFiles";
    private static final String EMPTY_STRING = "";

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(ctx ->
                !IMAGE_FILES.equals(ctx.getMapping() != null
                        ? ctx.getMapping().getLastDestinationProperty().getName()
                        : EMPTY_STRING));
        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}