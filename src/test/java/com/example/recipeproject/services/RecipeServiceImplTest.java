package com.example.recipeproject.services;

import com.example.recipeproject.converters.RecipeCommandToRecipe;
import com.example.recipeproject.converters.RecipeToRecipeCommand;
import com.example.recipeproject.domain.Recipe;
import com.example.recipeproject.exceptions.NotFoundException;
import com.example.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        Assertions.assertNotNull(recipeReturned, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeByIdTestNotFound() {

        Throwable thrown = Assertions.assertThrows(NotFoundException.class, () -> {

            Optional<Recipe> recipeOptional = Optional.empty();
            when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
            recipeService.findById(1L);
        });

        assertEquals("The recipe with id: 1 was not found.", thrown.getMessage());
    }

    @Test
    public void getRecipesTest() {
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(new Recipe());

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void deleteByIdTest() {

        //given
        Long idToDelete = 1L;

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}