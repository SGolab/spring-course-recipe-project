package com.example.recipeproject.services;

import com.example.recipeproject.commands.UnitOfMeasureCommand;
import com.example.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipeproject.domain.Ingredient;
import com.example.recipeproject.domain.UnitOfMeasure;
import com.example.recipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, converter);
    }

    @Test
    void listAllUoms() {

        //given
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);

        when(unitOfMeasureRepository.findAll()).thenReturn(Set.of(uom1, uom2));

        //when
        Set<UnitOfMeasureCommand> savedUoms = unitOfMeasureService.listAllUoms();

        //then
        assertEquals(2, savedUoms.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}