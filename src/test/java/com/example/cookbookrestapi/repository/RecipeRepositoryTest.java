package com.example.cookbookrestapi.repository;

import com.example.cookbookrestapi.model.Recipe;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeRepositoryTest {
    @Container
    static MySQLContainer<?> database = new MySQLContainer<>("mysql:8")
            .withDatabaseName("springboot")
            .withPassword("springboot")
            .withUsername("springboot");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    @Sql("/scripts/init-three-recipes.sql")
    void getAllBy() {
        PageRequest pageRequest = PageRequest.of(0,10,
                Sort.by("description"));
        List<Recipe> recipes =  recipeRepository.getAllBy(pageRequest);
        Assertions.assertEquals(3, recipes.size());
        Assertions.assertEquals("A", recipes.get(0).getDescription());
        Assertions.assertEquals("B", recipes.get(1).getDescription());
        Assertions.assertEquals("C", recipes.get(2).getDescription());
        Assertions.assertEquals(2, recipes.get(2).getChildRecipes().size());
        Assertions.assertEquals("A", recipes.get(2).getChildRecipes().get(0)
                .getDescription());
        Assertions.assertEquals("B", recipes.get(2).getChildRecipes().get(1)
                .getDescription());
        Assertions.assertEquals(0, recipes.get(0).getChildRecipes().size());
        Assertions.assertEquals(0, recipes.get(1).getChildRecipes().size());
    }
}