package com.my131.recipe_platform.controller;

import com.my131.recipe_platform.dto.IngredientRequestDto;
import com.my131.recipe_platform.dto.IngredientResponseDto;
import com.my131.recipe_platform.service.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    public IngredientResponseDto create(@Valid @RequestBody IngredientRequestDto ingredientRequestDto) {
        return ingredientService.create(ingredientRequestDto);
    }
}
