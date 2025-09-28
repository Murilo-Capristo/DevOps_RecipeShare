package br.com.fiap.RecipeShare.feature.recipe;

import br.com.fiap.RecipeShare.enums.Category;
import br.com.fiap.RecipeShare.enums.Difficulty;
import br.com.fiap.RecipeShare.feature.ingredientRecipe.IngredientRecipe;
import br.com.fiap.RecipeShare.feature.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{recipe.title}")
    @Size(min = 3, max = 100, message = "{recipe.titleSize}")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "{recipe.description}")
    @Size(min = 10, message = "{recipe.descriptionSize}")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Min(value = 1, message = "{recipe.portions")
    private int portions;

    @Min(value = 1, message = "{recipe.prepTime}")
    private int prepTime; // em minutos

    @NotNull(message = "{recipe.dificulty}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @NotNull(message = "{recipe.category}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private int likes;

    @NotBlank(message = "{recipe.imageUrl}")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "{recipe.imageUrlValid}"
    )
    private String imageUrl;

    // Relacionamento com usuário (autor da receita)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relacionamento com IngredientRecipe
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // evitar loop toString
    private List<IngredientRecipe> ingredients = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "recipe_likes", // tabela de join
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> usersWhoLiked = new HashSet<>();

    public boolean isLikedBy(User user) {
        return usersWhoLiked.contains(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<IngredientRecipe> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<User> getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public void setUsersWhoLiked(Set<User> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }
}
