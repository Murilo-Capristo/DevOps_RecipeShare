package br.com.fiap.RecipeShare.feature.recipe;

import jakarta.persistence.EntityNotFoundException;
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
        return recipeRepository.save(recipe);
    }

    public void deleteById(Long id){
        recipeRepository.delete(getRecipe(id));
    }

}
