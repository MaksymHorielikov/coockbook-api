package com.example.cookbookrestapi.repository;

import com.example.cookbookrestapi.model.Recipe;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> getAllBy(Pageable pageable);
    @Query(value = "SELECT COUNT(deleted) FROM recipes WHERE deleted = 0", nativeQuery = true)
    Long getCountRecipes();
}
