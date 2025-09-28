package br.com.fiap.RecipeShare.feature.ingredientRecipe;

import br.com.fiap.RecipeShare.enums.Unit;
import br.com.fiap.RecipeShare.feature.ingredient.Ingredient;

import br.com.fiap.RecipeShare.feature.recipe.Recipe;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com Recipe
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @ToString.Exclude  // bug de loop tostring
    private Recipe recipe;

    // Relacionamento com Ingredient
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    @ToString.Exclude  // bug de loop tostring
    private Ingredient ingredient;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", message = "Quantidade deve ser maior que 0")
    private double quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit unit; // Unidade da quantidade específica dessa receita

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
