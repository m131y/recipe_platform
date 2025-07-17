package com.my131.recipe_platform.controller;

import com.my131.recipe_platform.dto.IngredientRequestDto;
import com.my131.recipe_platform.dto.IngredientResponseDto;
import com.my131.recipe_platform.model.Ingredient;
import com.my131.recipe_platform.service.IngredientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    public Page<IngredientResponseDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return ingredientService.list(pageable);
    }

    @GetMapping("/{id}")
    public IngredientResponseDto get(@PathVariable Long id) {
        return ingredientService.get(id);
    }

    @PostMapping
    public IngredientResponseDto create(@Valid @RequestBody IngredientRequestDto ingredientRequestDto) {
        return ingredientService.create(ingredientRequestDto);
    }

    @PutMapping("/{id}")
    public IngredientResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody IngredientRequestDto ingredientRequestDto
    ) {
        return ingredientService.update(id, ingredientRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
