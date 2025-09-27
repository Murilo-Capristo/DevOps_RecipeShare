package br.com.fiap.RecipeShare.feature.recipe;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;


    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        var avatar = user.getAttributes().get("picture") != null?
                user.getAttributes().get("picture") :
                user.getAttributes().get("avatar_url");
        model.addAttribute("recipes", recipeService.findAllRecipes());
        model.addAttribute("user", user);
        model.addAttribute("avatar", avatar);
        return "index";

    }

    @GetMapping("/form")
    public String form(Recipe recipe){
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

    @PutMapping("/like/{id}")
    public String like(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        recipeService.incrementRecipeLikes(id, userService.register(principal));
        return "redirect:/recipe";
    }

    @PutMapping("/dislike/{id}")
    public String dislike(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        recipeService.decrementRecipeLikes(id, userService.register(principal));
        return "redirect:/recipe";
    }


}
