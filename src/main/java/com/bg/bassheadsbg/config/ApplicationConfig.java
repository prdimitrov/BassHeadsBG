package com.bg.bassheadsbg.config;

import com.bg.bassheadsbg.model.dto.accuweather.CityDTO;
import com.bg.bassheadsbg.model.entity.City;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean(name = "cityModelMapper")
//    public ModelMapper cityModelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//        // Custom property mapping for the City class!!!!!!!!
//        modelMapper.addMappings(new PropertyMap<CityDTO, City>() {
//            @Override
//            protected void configure() {
//                map().setCityKey(source.getKey());
//                map().setLocalizedName(source.getLocalizedName());
//                if (source.getAdministrativeArea() != null) {
//                    map().setAdministrativeAreaLocalizedName(source.getAdministrativeArea().getLocalizedName());
//                } else {
//                    map().setAdministrativeAreaLocalizedName(null); // Handle null as needed
//                }
//            }
//        });
//
//        return modelMapper;
//    }
}
