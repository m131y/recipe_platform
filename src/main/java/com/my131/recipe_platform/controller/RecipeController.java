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

    //전체조회 + 페이징
    @GetMapping
    public Page<RecipeResponseDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").descending());
        return recipeService.list(pageable);
    }

//    @GetMapping("/{id}")
//    public RecipeResponseDto get(@PathVariable Long id) {
//        return recipeService.getById(id);
//    }

    //단일조회
    @GetMapping("/{id}")
    public RecipeDetailDto get(@PathVariable Long id) {
        return recipeService.get(id);
    }

    @PostMapping
    public RecipeResponseDto create(@Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        return recipeService.create(recipeRequestDto);
    }

    @PostMapping("/{id}/ingredients/add")
    public ResponseEntity<Void> addIngredient(
            @PathVariable Long id,
            @Valid @RequestBody AddIngredientDto addIngredientDto
    ){
        recipeService.addIngredient(id, addIngredientDto);

        return ResponseEntity.ok().build();
    }

    //레시피를 삭제하는 게 아니라 레시피의 재료를 삭제하기 때문에 POST 요청 사용
    @PostMapping("/{id}/ingredients/{ingredientId}/remove")
    public ResponseEntity<Void> removeIngredient(
            @PathVariable Long id,
            @PathVariable Long ingredientId
    ) {
        recipeService.removeIngredient(id, ingredientId);

        return ResponseEntity.noContent().build();
    }
}
