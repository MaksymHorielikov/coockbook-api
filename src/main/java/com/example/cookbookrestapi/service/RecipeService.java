package com.example.cookbookrestapi.service;

import com.example.cookbookrestapi.model.Recipe;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface RecipeService {
    List<Recipe> findAll(PageRequest pageRequest);

    Recipe save(Recipe recipe);

    Recipe findById(Long id);

    void deleteById(Long id);

    List<Recipe> findVersionsById(Long id);

    Long getCountRecipes();
}
