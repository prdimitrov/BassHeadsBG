package com.bg.bassheadsbg.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ListUrlsValidator implements ConstraintValidator<ValidUrlList, List<String>> {

    @Override
    public void initialize(ValidUrlList constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> urls, ConstraintValidatorContext context) {
        if (urls == null || urls.isEmpty()) {
            return false; // Null or empty list is considered invalid
        }

        for (String url : urls) {
            if (url == null || url.trim().isEmpty() || !isValidUrl(url)) {
                return false; // Invalid URL found
            }
        }
        return true; // All URLs are valid
    }

    private boolean isValidUrl(String url) {
        return url.matches("^(http|https)://.+");
    }
}