package com.my131.recipe_platform.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    // 기본키
    // 생성전략 : 자동생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB단에서 null 방지 (물리적 제약), unique : 중복 방지
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    // 일대다관계, mappedBy:이 필드는 관계의 주인이 아님, 상대 엔티티의 ingredient필드가 주인.
    // cascade = CascadeType.ALL : 연관된 자식 엔티티도 동일한 작업을 함께 자동으로 수행
    // orphanRemoval = true도 함께 설정해야 자식 고아 객체도 확실히 제거
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)

    // 자바 직렬화 순환 방지 대책, 이 필드는 직렬화 대상.
    // JsonManagedReference를 여러 개 사용 시 이름 지정해 쌍을 지어야함.
    // ingredient-ri로 RecipeIngredient의 ingredient와 쌍을 이룸
    @JsonManagedReference("ingredient-ri")
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
}
