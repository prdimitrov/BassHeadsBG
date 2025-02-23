package com.bg.bassheadsbg.validation.imagesValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class NotEmptyImageFilesValidator implements ConstraintValidator<NotEmptyImageFiles, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        return files != null && !files.isEmpty() && files.stream().anyMatch(file -> !file.isEmpty());
    }
}