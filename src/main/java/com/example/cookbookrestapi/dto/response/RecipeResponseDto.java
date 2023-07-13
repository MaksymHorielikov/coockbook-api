package com.example.cookbookrestapi.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class RecipeResponseDto {
    private Long id;
    private String description;
    private LocalDateTime dateCreated;
    private List<RecipeResponseDto> childRecipes;
}
