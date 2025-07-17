package com.my131.recipe_platform.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequestDto {
    @NotBlank(message = "이름을 입력하세요.")
    private String name;
}
