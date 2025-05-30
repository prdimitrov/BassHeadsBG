package com.bg.bassheadsbg.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "forex.api")
public class ForexApiConfig {

    private static final String KEY = "key";
    private static final String BASE = "base";
    private static final String URL = "url";
    private static final String USD = "USD";
    private static final String SORRY_FREE_API = "Sorry, but the free API does not support base, "
            + "currencies different than " + USD + ".";
    private static final String PROPERTY = "Property ";
    private static final String CANNOT_BE_EMPTY = " cannot be empty.";
    private String key;

    private String url;

    private String base;

    public String getKey() {
        return key;
    }

    public ForexApiConfig setKey(String key) {
        this.key = key;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ForexApiConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getBase() {
        return base;
    }

    public ForexApiConfig setBase(String base) {
        this.base = base;
        return this;
    }

    @PostConstruct
    public void checkConfiguration() {

        verifyNotNullOrEmpty(KEY, key);
        verifyNotNullOrEmpty(BASE, base);
        verifyNotNullOrEmpty(URL, url);

        if (!USD.equals(base)) {
            throw new IllegalStateException(SORRY_FREE_API);
        }

    }

    private static void verifyNotNullOrEmpty(String name, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(PROPERTY + name + CANNOT_BE_EMPTY);
        }
    }

}