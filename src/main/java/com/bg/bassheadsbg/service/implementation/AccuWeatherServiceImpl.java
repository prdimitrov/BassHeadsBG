package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.entity.City;
import com.bg.bassheadsbg.repository.CityRepository;
import com.bg.bassheadsbg.service.interfaces.AccuWeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.MalformedLinkException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

    public AccuWeatherServiceImpl(CityRepository cityRepository, RestTemplate restTemplate) {
        this.cityRepository = cityRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public List<String> getAllTownsInBulgaria() {
        List<String> townsList = new ArrayList<>();
        try {
            URL url = new URL(BASE_URL + COUNTRY_CODE + "?apikey=" + API_KEY);

            // Making an API call
            String jsonResponse = restTemplate.getForObject(url.toString(), String.class);

            // Parsing the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(jsonResponse);

            for (JsonNode cityNode : node) {
                String key = cityNode.get("Key").asText();
                String localizedName = cityNode.get("LocalizedName").asText();

                townsList.add(localizedName);

                City city = new City();
                city.setCityKey(key);
                city.setLocalizedName(localizedName);
                cityRepository.save(city);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve towns!\n----------------------------------" + e);
        }
        return townsList;
    }

    public boolean hasInitializedTowns() {
        return cityRepository.count() > 0;
    }
}
