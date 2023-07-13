package com.example.cookbookrestapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RecipeRequestDto {
    @NotBlank(message = "Description cannot blank")
    private String description;
    @Positive(message = "Parent id must only positive value")
    private Long parentId;
}
