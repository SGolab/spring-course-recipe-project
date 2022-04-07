package com.example.recipeproject.services;

import com.example.recipeproject.commands.UnitOfMeasureCommand;
import com.example.recipeproject.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
