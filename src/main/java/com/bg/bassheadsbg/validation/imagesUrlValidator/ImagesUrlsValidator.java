package com.bg.bassheadsbg.validation.imagesUrlValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ImagesUrlsValidator implements ConstraintValidator<ValidUrlSet, Set<String>> {

    @Override
    public void initialize(ValidUrlSet constraintAnnotation) {
    }

    @Override
    public boolean isValid(Set<String> urls, ConstraintValidatorContext context) {
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