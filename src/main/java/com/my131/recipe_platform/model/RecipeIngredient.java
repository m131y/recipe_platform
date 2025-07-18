package com.my131.recipe_platform.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient {
    // @EmbeddedId = 복합 기본 키 식별자
    @EmbeddedId
    private RecipeIngredientId id;

    // 다대일관계
    // fetch = FetchType.LAZY : 지연 로딩 전략 설정, recipe는 실제로 사용할 때까지 DB에서 조회하지 않음
    @ManyToOne(fetch = FetchType.LAZY)
    // RecipeIngredientId의 recipeId와 이 recipe 필드를 자동으로 동기화
    // recipe.getId()값을 자동으로 RecipeIngredientId의 recipeId에 넣어줌
    @MapsId("recipeId")
    // recipe_id라는 외래 키(FK) 컬럼을 생성
    @JoinColumn(name = "recipe_id")
    // 자바 직렬화 순환 방지 대책, 이 필드는 직렬화 제외.
    // JsonBackReference를 여러 개 사용 시 이름 지정해 쌍을 지어야함.
    // recipe-ri = recipe-recipeingredient(약어 : ri)
    // recipe-ri로 Recipe의 recipeIngredients와 쌍을 이룸
    @JsonBackReference("recipe-ri")
    private Recipe recipe;

    // 다대일관계
    // fetch = FetchType.LAZY : 지연 로딩 전략 설정, ingredient는 실제로 사용할 때까지 DB에서 조회하지 않음
    @ManyToOne(fetch = FetchType.LAZY)
    // RecipeIngredientId의 ingredientId와 이 ingredient 필드를 자동으로 동기화
    // ingredient.getId()값을 자동으로 RecipeIngredientId의 ingredientId에 넣어줌
    @MapsId("IngredientId")
    // ingredient_id라는 외래 키(FK) 컬럼을 생성
    @JoinColumn(name = "ingredient_id")
    // 자바 직렬화 순환 방지 대책, 이 필드는 직렬화 제외.
    // JsonBackReference를 여러 개 사용 시 이름 지정해 쌍을 지어야함.
    // ingredient-ri = ingredient-recipeingredient(약어 : ri)
    // ingredient-ri로 Ingredient의 recipeIngredients와 쌍을 이룸
    @JsonBackReference("ingredient-ri")
    private Ingredient ingredient;

    // 50자 제한
    @Column(length = 50)
    private String quantity;
}
