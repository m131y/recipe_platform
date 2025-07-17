package com.my131.recipe_platform.repository;

import com.my131.recipe_platform.model.RecipeIngredient;
import com.my131.recipe_platform.model.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
}
