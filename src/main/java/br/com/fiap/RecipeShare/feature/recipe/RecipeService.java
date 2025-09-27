package br.com.fiap.RecipeShare.feature.recipe;

import br.com.fiap.RecipeShare.enums.Unit;
import br.com.fiap.RecipeShare.feature.ingredient.Ingredient;
import br.com.fiap.RecipeShare.feature.ingredientRecipe.IngredientRecipe;
import br.com.fiap.RecipeShare.feature.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;


    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public Recipe getRecipe(Long id){
        return recipeRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found")
                );
    }

    public List<Recipe> findAllRecipes(){
        return recipeRepository.findAll();
    }

    public Recipe save(Recipe recipe){
        // seta a referência da receita em cada IngredientRecipe
        for (IngredientRecipe ir : recipe.getIngredients()) {
            ir.setRecipe(recipe);
        }
        return recipeRepository.save(recipe);
    }

    public void deleteById(Long id){
        recipeRepository.delete(getRecipe(id));
    }

    public void decrementRecipeLikes(Long id, User user){
        var recipe = getRecipe(id);
        if (recipe.getLikes() <= 0){
            recipe.setLikes(0);
            return;
        }
        recipe.setLikes(recipe.getLikes() - 1);
        recipeRepository.save(recipe);

    }

    public void incrementRecipeLikes(Long id, User user){
        var recipe = getRecipe(id);
        recipe.setLikes(recipe.getLikes() + 1);
        recipeRepository.save(recipe);
    }


}
