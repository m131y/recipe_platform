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
    @EmbeddedId
    private RecipeIngredientId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    //@JsonBackReference를 많이 쓸 때 이름지어 구분
    //recipe-ri = recipe-recipeingredient(약어 : ri)
    //@OneToMapny의 @JsonManagedReference와 recipe-ri로 연결
    @JsonBackReference("recipe-ri")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    //ingredient-ri = ingredient-recipeingredient(약어 : ri)
    //@OneToMapny의 @JsonManagedReference와 ingredient-ri로 연결
    @JsonBackReference("ingredient-ri")
    private Ingredient ingredient;

    @Column(length = 50)
    private String quantity;
}
