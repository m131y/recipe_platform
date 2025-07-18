package com.my131.recipe_platform.controller;

import com.my131.recipe_platform.dto.AddIngredientDto;
import com.my131.recipe_platform.dto.RecipeDetailDto;
import com.my131.recipe_platform.dto.RecipeRequestDto;
import com.my131.recipe_platform.dto.RecipeResponseDto;

import com.my131.recipe_platform.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    // 전체조회 + 페이징
    @GetMapping
    public Page<RecipeResponseDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").descending());
        return recipeService.list(pageable);
    }

    // 단일조회
    @GetMapping("/{id}")
    public RecipeDetailDto get(@PathVariable Long id) {
        return recipeService.get(id);
    }

    // 레시피 생성
    @PostMapping
    public RecipeResponseDto create(@Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        return recipeService.create(recipeRequestDto);
    }

    // 레시피 수정
    @PutMapping("/{id}")
    public RecipeResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody RecipeRequestDto recipeRequestDto
    ) {
        return recipeService.update(id, recipeRequestDto);
    }

    // 레시피 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // 레시피에 재료 추가
    @PostMapping("/{id}/ingredients/add")
    public ResponseEntity<Void> addIngredient(
            @PathVariable Long id,
            @Valid @RequestBody AddIngredientDto addIngredientDto
    ){
        recipeService.addIngredient(id, addIngredientDto);

        return ResponseEntity.ok().build();
    }

    // 레시피에 재료 삭제
    @DeleteMapping("/{id}/ingredients/{ingredientId}/remove")
    public ResponseEntity<Void> removeIngredient(
            @PathVariable Long id,
            @PathVariable Long ingredientId
    ) {
        recipeService.removeIngredient(id, ingredientId);

        return ResponseEntity.noContent().build();
    }
}
