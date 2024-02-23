package com.example.shiftbet.web.dto;

import com.example.shiftbet.domain.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryDTO {

    @NotBlank(message = "Name is required")
    private String name;

    private String imageUrl;

    @NotNull(message = "Parent category is required")
    private Category category;

}
