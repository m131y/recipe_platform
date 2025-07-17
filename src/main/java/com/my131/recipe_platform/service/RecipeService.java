package com.my131.recipe_platform.service;

import com.my131.recipe_platform.dto.*;
import com.my131.recipe_platform.model.Ingredient;
import com.my131.recipe_platform.model.Recipe;

import com.my131.recipe_platform.model.RecipeIngredient;
import com.my131.recipe_platform.model.RecipeIngredientId;
import com.my131.recipe_platform.repository.IngredientRepository;
import com.my131.recipe_platform.repository.RecipeIngredientRepository;
import com.my131.recipe_platform.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

//    public RecipeResponseDto getById(Long id) {
//        Recipe recipe = recipeRepository.findById(id)
//                .orElseThrow(()-> new NoSuchElementException("해당 레시피가 없습니다."));
//        RecipeResponseDto recipeResponseDto = new RecipeResponseDto();
//
//        return ;
//    }

    //전체조회 + 페이징
    public Page<RecipeResponseDto> list (Pageable pageable) {
        return recipeRepository.findAll(pageable)
                .map(recipe -> new RecipeResponseDto(recipe.getId(), recipe.getTitle(), recipe.getDescription()));
    }
    //단일조회
    public RecipeDetailDto get(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 레시피가 없습니다."));
        List<RecipeIngredientDto> ingredientDtos = recipe.getRecipeIngredients().stream()
                .map(
                        recipeIngredient -> new RecipeIngredientDto(
                                recipeIngredient.getIngredient().getId(),
                                recipeIngredient.getIngredient().getName(),
                                recipeIngredient.getQuantity()
                        )
                ).toList();

        return new RecipeDetailDto(recipe.getId(), recipe.getTitle(), recipe.getDescription(), ingredientDtos );
    }

    public RecipeResponseDto create(RecipeRequestDto recipeRequestDto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeRequestDto.getTitle());
        recipe.setDescription(recipeRequestDto.getDescription());

        Recipe saved = recipeRepository.save(recipe);

        return new RecipeResponseDto(saved.getId(), saved.getTitle(), saved.getDescription());
    }

    public RecipeResponseDto update(Long id, RecipeRequestDto recipeRequestDto) {
        Recipe existRecipe = recipeRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("해당 레시피가 없습니다."));

        existRecipe.setTitle(recipeRequestDto.getTitle());
        existRecipe.setDescription(recipeRequestDto.getDescription());

        Recipe saved = recipeRepository.save(existRecipe);

        return new RecipeResponseDto(saved.getId(), saved.getTitle(), saved.getDescription());
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    // 레시피에 재료를 추가
    public void addIngredient(Long recipeId, AddIngredientDto addIngredientDto) {
        // 해당 레시피가 있어야 추가가능
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("레시피를 찾을 수 없습니다."));
        // 추가할 재료가 있어야 추가가능
        Ingredient ingredient = ingredientRepository.findById(addIngredientDto.getIngredientId())
                .orElseThrow(() -> new NoSuchElementException("재료를 찾을 수 없습니다."));

        RecipeIngredientId id = new RecipeIngredientId(recipe.getId(), ingredient.getId());
        //이미 등록된 조합(recipeID, ingredientID)인지 확인
        if (recipeIngredientRepository.existsById(id)) {
            throw new IllegalArgumentException("이미 추가된 재료입니다.");
        }

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(id);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(addIngredientDto.getQuantity());

        // recipeIngredients는 List<RecipeIngredient> 이므로 setRecipeIngredient()가 아닌 add로 추가
        recipe.getRecipeIngredients().add(recipeIngredient);

        // cascade 옵션 덕분에 recipe에서 save하면 recipeingredient에서도 save
        recipeRepository.save(recipe);
    }
    // 재료 삭제
    public void removeIngredient(Long recipeId, Long IngredientId) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, IngredientId);
        recipeIngredientRepository.deleteById(id);
    }


}
