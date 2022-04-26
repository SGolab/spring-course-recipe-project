package com.example.recipeproject.controllers;

import com.example.recipeproject.commands.RecipeCommand;
import com.example.recipeproject.domain.Recipe;
import com.example.recipeproject.exceptions.NotFoundException;
import com.example.recipeproject.repositories.RecipeRepository;
import com.example.recipeproject.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jt on 6/19/17.
 */
public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test()
    public void testGetRecipeNotFound() throws Exception {

        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void testGetRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}