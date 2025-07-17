package com.my131.recipe_platform.repository;

import com.my131.recipe_platform.model.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
