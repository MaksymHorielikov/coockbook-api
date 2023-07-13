package com.example.cookbookrestapi.controller;

import com.example.cookbookrestapi.dto.mapper.DtoMapper;
import com.example.cookbookrestapi.dto.request.RecipeRequestDto;
import com.example.cookbookrestapi.dto.response.RecipeResponseDto;
import com.example.cookbookrestapi.model.Recipe;
import com.example.cookbookrestapi.service.RecipeService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final DtoMapper<Recipe, RecipeRequestDto, RecipeResponseDto> recipeMapper;
    private final RecipeService recipeService;

    @GetMapping
    public List<RecipeResponseDto> getAllRecipes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer count) {
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by("description"));
        return recipeService.findAll(pageRequest).stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{recipeId}")
    public RecipeResponseDto getRecipeById(@PathVariable Long recipeId) {
        return recipeMapper.toDto(recipeService.findById(recipeId));
    }

    @GetMapping("/{recipeId}/versions")
    public List<RecipeResponseDto> getRecipeVersions(@PathVariable Long recipeId) {
        return recipeService.findVersionsById(recipeId).stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public RecipeResponseDto createRecipe(@RequestBody @Valid RecipeRequestDto recipeRequestDto) {
        Recipe recipe = recipeMapper.toModel(recipeRequestDto);
        recipe.setDateCreated(LocalDateTime.now());
        return recipeMapper.toDto(recipeService.save(recipe));
    }

    @PostMapping("/{parentId}/fork")
    public RecipeResponseDto forkRecipe(@PathVariable Long parentId,
                                        @RequestBody @Valid RecipeRequestDto childRecipe) {
        Recipe parentRecipe = recipeService.findById(parentId);
        Recipe forkedRecipe = new Recipe();
        forkedRecipe.setDescription(childRecipe.getDescription());
        forkedRecipe.setDateCreated(LocalDateTime.now());
        forkedRecipe.setParent(parentRecipe);
        return recipeMapper.toDto(recipeService.save(forkedRecipe));
    }

    @PutMapping("/{recipeId}")
    public RecipeResponseDto updateRecipe(@PathVariable Long recipeId,
                                          @RequestBody @Valid RecipeRequestDto recipeRequestDto) {
        Recipe recipe = recipeMapper.toModel(recipeRequestDto);
        Recipe recipeById = recipeService.findById(recipeId);
        recipe.setId(recipeById.getId());
        recipe.setDateCreated(recipeById.getDateCreated());
        return recipeMapper.toDto(recipeService.save(recipe));
    }

    @DeleteMapping("/{recipeId}")
    public void deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteById(recipeId);
    }
}
