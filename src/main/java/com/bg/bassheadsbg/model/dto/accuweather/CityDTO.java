package com.bg.bassheadsbg.model.dto.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // This will ignore all unknown properties?!
public class CityDTO {
    @JsonProperty("Key")
    private String key;

    @JsonProperty("LocalizedName")
    private String localizedName;

    @JsonProperty("AdministrativeArea")
    private AdministrativeAreaDTO administrativeArea;
}