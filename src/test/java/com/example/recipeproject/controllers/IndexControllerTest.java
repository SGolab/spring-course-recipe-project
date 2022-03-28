package com.example.recipeproject.controllers;

import com.example.recipeproject.domain.Recipe;
import com.example.recipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IndexControllerTest {

    IndexController controller;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        controller = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {

        //preparing data
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);
        recipes.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //performing the operations
        String viewName = controller.getIndexPage(model);

        //testing if operations' results are expected

        assertEquals(viewName, "index"); // proper view name is returned
        verify(recipeService, times(1)).getRecipes(); //get recipes method of model was called once (by Index Controller)
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture()); //attribute "recipes" was added once to the Model

        Set<Recipe> setInController = argumentCaptor.getValue(); //proper Set<Recipe> was passed to Model via Index Controller
        assertEquals(2, setInController.size());
    }
}