package com.example.cookbookrestapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.cookbookrestapi.dto.mapper.RecipeMapper;
import com.example.cookbookrestapi.dto.response.RecipeResponseDto;
import com.example.cookbookrestapi.model.Recipe;
import com.example.cookbookrestapi.service.RecipeService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RecipeControllerTest1 {
    private RecipeController recipeController;
    @Mock
    private RecipeService recipeService;

    @Mock
    private RecipeMapper recipeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeMapper, recipeService);
    }

    @Test
    void testGetRecipeById() {
        Recipe recipe = new Recipe(1L, "test recipe",
                LocalDateTime.of(2023,7,13,9,0));
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto();
        recipeResponseDto.setId(1L);
        recipeResponseDto.setDescription("test recipe");
        recipeResponseDto.setDateCreated(
                LocalDateTime.of(2023,7,13,9,0));
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        when(recipeMapper.toDto(any(Recipe.class))).thenReturn(recipeResponseDto);
        RecipeResponseDto responseDto = recipeController.getRecipeById(1L);
        assertEquals(recipeResponseDto, responseDto);
        verify(recipeService, times(1)).findById(anyLong());
        verify(recipeMapper, times(1)).toDto(any(Recipe.class));
    }
}
