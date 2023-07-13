package com.example.cookbookrestapi.controller;

import com.example.cookbookrestapi.model.Recipe;
import com.example.cookbookrestapi.service.RecipeService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest2 {
    @MockBean
    private RecipeService recipeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldShowAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipeFirst = new Recipe();
        recipeFirst.setDescription("A");
        Recipe recipeSecond = new Recipe();
        recipeSecond.setDescription("B");
        recipes.add(recipeFirst);
        recipes.add(recipeSecond);
        PageRequest pageRequest = PageRequest.of(0,10,
                Sort.by("description"));
        Mockito.when(recipeService.findAll(pageRequest)).thenReturn(recipes);
        RestAssuredMockMvc.when()
                .get("/recipes")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].description",Matchers.equalTo("A" ))
                .body("[1].description",Matchers.equalTo("B" ));
    }
}