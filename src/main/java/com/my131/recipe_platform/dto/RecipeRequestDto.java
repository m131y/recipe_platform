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
public class RecipeRequestDto {
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    private String description;
}
