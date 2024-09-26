package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.accuweather.CityDTO;
import com.bg.bassheadsbg.model.entity.City;
import com.bg.bassheadsbg.repository.CityRepository;
import com.bg.bassheadsbg.service.interfaces.AccuWeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

@Slf4j
@Service
public class AccuWeatherServiceImpl implements AccuWeatherService {
    private static final String BASE_URL = "http://dataservice.accuweather.com/locations/v1/cities/";
    private static final String COUNTRY_CODE = "BG";
    @Value("${accuweather.accu_key}")
    private String API_KEY;

    private final CityRepository cityRepository;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    public AccuWeatherServiceImpl(CityRepository cityRepository, RestTemplate restTemplate, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void initializeAllCitiesInBulgaria() {
        try {
            URL url = new URL(BASE_URL + COUNTRY_CODE + "?apikey=" + API_KEY);

            // Making API call
            String jsonResponse = restTemplate.getForObject(url.toString(), String.class);

            // Parsing the JSON response into an CityDTOs array!
            ObjectMapper objectMapper = new ObjectMapper();
            CityDTO[] cityDTOs = objectMapper.readValue(jsonResponse, CityDTO[].class);

            for (CityDTO cityDTO : cityDTOs) {
                City city = modelMapper.map(cityDTO, City.class);
                cityRepository.save(city);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve towns!\n----------------------------------{}", e.toString());
        }
    }

    public boolean hasInitializedCities() {
        return cityRepository.count() > 0;
    }

    public List<String> getAllCitiesFromDb() {
        return cityRepository.findAllByLocalizedNames();
    }
}
