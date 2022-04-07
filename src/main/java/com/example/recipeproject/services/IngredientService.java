package com.example.recipeproject.services;

import com.example.recipeproject.commands.IngredientCommand;
import com.example.recipeproject.domain.Ingredient;

import java.util.Set;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);
}
