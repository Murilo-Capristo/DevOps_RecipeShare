package br.com.fiap.RecipeShare.feature.recipe;


import br.com.fiap.RecipeShare.feature.ingredient.IngredientService;
import br.com.fiap.RecipeShare.feature.user.User;
import br.com.fiap.RecipeShare.feature.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;
    private final IngredientService ingredientService;




    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        var avatar = user.getAttributes().get("picture") != null?
                user.getAttributes().get("picture") :
                user.getAttributes().get("avatar_url");
        model.addAttribute("recipes", recipeService.findAllRecipes());
        model.addAttribute("user", user);
        model.addAttribute("avatar", avatar);

        var recipes = recipeService.findAllRecipes();
        User loggedUser = userService.register(user);

        return "index";


    }

    @GetMapping("{id}")
    public String details(@PathVariable Long id, Model model){
        Recipe recipe = recipeService.getRecipe(id);
        model.addAttribute("recipe", recipe);
        return "details";
    }

    @GetMapping("/form")
    public String form(Model model, Recipe recipe){
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", ingredientService.findAll());
        return "form";
    }

    @PostMapping("/form")
    public String create(@Valid Recipe recipe, BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()){
            return "form";
        }
        recipeService.save(recipe);
        redirect.addFlashAttribute("message", "Recipe created successfully");
        return "redirect:/recipe";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        recipeService.deleteById(id);
        redirect.addFlashAttribute("message", "Recipe deleted successfully");
        return "redirect:/recipe";
    }

    @PutMapping("/toggle-like/{id}")
    public String toggleLike(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        Recipe recipe = recipeService.getRecipe(id);

        if (recipe.getUsersWhoLiked().contains(user)) {
            recipeService.decrementRecipeLikes(id, user);
        } else {
            recipeService.incrementRecipeLikes(id, user);
        }

        return "redirect:/recipe";
    }




}
