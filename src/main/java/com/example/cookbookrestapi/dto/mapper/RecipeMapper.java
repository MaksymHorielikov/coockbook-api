package com.example.cookbookrestapi.dto.mapper;

import com.example.cookbookrestapi.dto.request.RecipeRequestDto;
import com.example.cookbookrestapi.dto.response.RecipeResponseDto;
import com.example.cookbookrestapi.model.Recipe;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper implements DtoMapper<Recipe, RecipeRequestDto, RecipeResponseDto> {
    @Override
    public Recipe toModel(RecipeRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setDescription(dto.getDescription());
        if (dto.getParentId() != null) {
            Recipe parentRecipe = new Recipe();
            parentRecipe.setId(dto.getParentId());
            recipe.setParent(parentRecipe);
        }
        return recipe;
    }

    @Override
    public RecipeResponseDto toDto(Recipe recipe) {
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto();
        recipeResponseDto.setId(recipe.getId());
        recipeResponseDto.setDescription(recipe.getDescription());
        recipeResponseDto.setDateCreated(recipe.getDateCreated());
        recipeResponseDto.setChildRecipes(recipe.getChildRecipes().stream()
                .sorted(Comparator.comparing(Recipe::getDescription))
                .map(this::toDto)
                .collect(Collectors.toList()));
        return recipeResponseDto;
    }
}
