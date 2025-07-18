package com.my131.recipe_platform.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.sql.results.graph.collection.internal.ListInitializer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    // 기본키
    // 생성전략 : 자동생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB단에서 null 방지 (물리적 제약)
    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // 일대다관계, mappedBy:이 필드는 관계의 주인이 아님, 상대 엔티티의 recipe필드가 주인.
    // cascade = CascadeType.ALL : 연관된 자식 엔티티도 동일한 작업을 함께 자동으로 수행
    // orphanRemoval = true도 함께 설정해야 자식 고아 객체도 확실히 제거
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    // 자바 직렬화 순환 방지 대책, 이 필드는 직렬화 대상.
    // JsonManagedReference를 여러 개 사용 시 이름 지정해 쌍을 지어야함.
    // recipe-ri로 RecipeIngredient의 recipe와 쌍을 이룸

    @JsonManagedReference("recipe-ri")
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
}
