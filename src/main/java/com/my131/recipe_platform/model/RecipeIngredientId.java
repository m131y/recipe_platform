package com.my131.recipe_platform.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
// 이 클래스가 엔티티 내부에서 식별자(키)로 내장됨을 JPA에 알림
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
// 두 개의 키로 객체를 비교할 때 필요한 로직 자동 생성 → 식별자 비교의 핵심
@EqualsAndHashCode
// implements Serializable : JPA가 이 객체를 직렬화 가능 객체로 처리
public class RecipeIngredientId implements Serializable {
    private Long recipeId;
    private Long ingredientId;
}
