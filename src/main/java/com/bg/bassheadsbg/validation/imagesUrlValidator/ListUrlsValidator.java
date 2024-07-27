package com.bg.bassheadsbg.validation.imagesUrlValidator;

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
            return false;
        }

        for (String url : urls) {
            if (url == null || url.trim().isEmpty() || !isValidUrl(url)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidUrl(String url) {
        return url.matches("^(http|https):.+");
    }
}