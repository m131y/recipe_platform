package com.my131.recipe_platform.service;

import com.my131.recipe_platform.dto.IngredientRequestDto;
import com.my131.recipe_platform.dto.IngredientResponseDto;
import com.my131.recipe_platform.model.Ingredient;
import com.my131.recipe_platform.repository.IngredientRepository;
import com.my131.recipe_platform.repository.RecipeIngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    //전체조회 + 페이징
    public Page<IngredientResponseDto> list(Pageable pageable) {
        return ingredientRepository.findAll(pageable).map(ingredient -> new IngredientResponseDto(
                ingredient.getId(),
                ingredient.getName()
        ));
    }

    //단일조회
    public IngredientResponseDto get(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("재료를 찾을 수 없습니다."));
        return new IngredientResponseDto(
                ingredient.getId(),
                ingredient.getName()
        );
    }

    public IngredientResponseDto create(IngredientRequestDto ingredientRequestDto) {
        if(ingredientRepository.findByName(ingredientRequestDto.getName()).isPresent()) {
            throw new IllegalArgumentException("재료가 이미 존재합니다.");
        }

        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientRequestDto.getName());

        Ingredient saved = ingredientRepository.save(ingredient);

        return new IngredientResponseDto(saved.getId(), saved.getName());
    }

    public IngredientResponseDto update(Long id, IngredientRequestDto ingredientRequestDto) {
        Ingredient existingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("재료가 존재하지 않습니다."));

        existingredient.setName(ingredientRequestDto.getName());

        Ingredient saved = ingredientRepository.save(existingredient);

        return new IngredientResponseDto(saved.getId(), saved.getName());
    }

    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }
}
