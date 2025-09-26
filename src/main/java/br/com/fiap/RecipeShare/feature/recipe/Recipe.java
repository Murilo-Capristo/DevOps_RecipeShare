package br.com.fiap.RecipeShare.feature.recipe;

import br.com.fiap.RecipeShare.enums.Category;
import br.com.fiap.RecipeShare.enums.Difficulty;
import br.com.fiap.RecipeShare.feature.ingredientRecipe.IngredientRecipe;
import br.com.fiap.RecipeShare.feature.user.User;
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
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int portions;

    private int prepTime; // em minutos

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private int likes = 0;

    private String imageUrl;

    // Relacionamento com usuário (autor da receita)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relacionamento com IngredientRecipe
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientRecipe> ingredients;
}
