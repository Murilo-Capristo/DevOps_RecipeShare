package br.com.fiap.RecipeShare.feature.recipe;

import br.com.fiap.RecipeShare.feature.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByOrderByTitle();
}
