package com.bg.bassheadsbg.model.dto.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageListDetailsDTO {
    public String tableName;

    public List<String> imageUrls;
}
