package com.my131.recipe_platform.service;

import com.my131.recipe_platform.dto.IngredientRequestDto;
import com.my131.recipe_platform.dto.IngredientResponseDto;
import com.my131.recipe_platform.model.Ingredient;
import com.my131.recipe_platform.repository.IngredientRepository;
import com.my131.recipe_platform.repository.RecipeIngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public IngredientResponseDto create(IngredientRequestDto ingredientRequestDto) {
        if(ingredientRepository.findByName(ingredientRequestDto.getName()).isPresent()) {
            throw new IllegalArgumentException("재료가 이미 존재합니다.");
        }

        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientRequestDto.getName());

        Ingredient saved = ingredientRepository.save(ingredient);

        return new IngredientResponseDto(saved.getId(), saved.getName());
    }
}
