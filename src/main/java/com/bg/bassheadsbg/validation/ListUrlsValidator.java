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
            return false; // Null or empty set is considered invalid
        }

        for (String url : urls) {
            if (!isValidUrl(url)) {
                return false; // Invalid URL found
            }
        }
        return true; // All URLs are valid
    }

    private boolean isValidUrl(String url) {
        // Implement your URL validation logic here
        // Example: Simple URL validation using regex
        return url != null && url.matches("^(http|https)://.+");
    }
}