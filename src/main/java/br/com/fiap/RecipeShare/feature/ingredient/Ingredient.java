package br.com.fiap.RecipeShare.feature.ingredient;

import br.com.fiap.RecipeShare.enums.Unit;
import br.com.fiap.RecipeShare.feature.ingredientRecipe.IngredientRecipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit defaultUnit; // g, ml, un, colheres etc

    // Relacionamento com IngredientRecipe
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientRecipe> recipes;



}
