package com.example.cookbookrestapi.service;

import com.example.cookbookrestapi.model.Recipe;
import com.example.cookbookrestapi.repository.RecipeRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll(PageRequest pageRequest) {
        return recipeRepository.getAllBy(pageRequest);
    }

    @Override
    public Recipe save(Recipe recipe) {
        if (recipe.getParent() != null) {
            Long parentId = recipe.getParent().getId();
            if (parentId == recipe.getId()) {
                throw new RuntimeException("Parent id cannot be equal to recipe id");
            }
            recipeRepository.findById(parentId).orElseThrow(() ->
                    new RuntimeException("Can't find the recipe by id = " + parentId
                            + " which is specified as the parent"));
            boolean isCyclicDependencyRecipes = findVersionsById(parentId).stream()
                    .anyMatch(r -> r.getId() == recipe.getId());
            if (isCyclicDependencyRecipes) {
                throw new RuntimeException("Change parent id to value " + parentId
                        + " impossible due to cyclic dependency of recipes");
            }
        }
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find the recipe by id = " + id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find the recipe by id = " + id));
        recipeRepository.deleteById(id);
    }

    @Override
    public List<Recipe> findVersionsById(Long id) {
        List<Recipe> versions = new ArrayList<>();
        Recipe currentRecipe = findById(id).getParent();
        while (currentRecipe != null) {
            versions.add(currentRecipe);
            currentRecipe = currentRecipe.getParent();
        }
        return versions.stream()
                .sorted(Comparator.comparing(Recipe::getDescription))
                .collect(Collectors.toList());
    }

    @Override
    public Long getCountRecipes() {
        return recipeRepository.getCountRecipes();
    }
}
