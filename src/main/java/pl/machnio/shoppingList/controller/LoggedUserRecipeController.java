package pl.machnio.shoppingList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.machnio.shoppingList.entity.IngredientWithQuantity;
import pl.machnio.shoppingList.entity.Recipe;
import pl.machnio.shoppingList.entity.SetOfIngredientsWithQuantities;
import pl.machnio.shoppingList.service.IngredientWithQuantityService;
import pl.machnio.shoppingList.service.RecipeService;
import pl.machnio.shoppingList.service.SetOfIngredientsWithQuantitiesService;

import javax.validation.Valid;

@Controller
@RequestMapping("/logged-user/recipe")
public class LoggedUserRecipeController {

    private final RecipeService recipeService;
    private final IngredientWithQuantityService ingredientWithQuantityService;
    private final SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService;

    public LoggedUserRecipeController(RecipeService recipeService, IngredientWithQuantityService ingredientWithQuantityService, SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService) {
        this.recipeService = recipeService;
        this.ingredientWithQuantityService = ingredientWithQuantityService;
        this.setOfIngredientsWithQuantitiesService = setOfIngredientsWithQuantitiesService;
    }

    @GetMapping("/create-set-of-ingredients")
    public String addIngredientsToRecipe(Model model) {
        model.addAttribute("ingredientsWithQuantity", new IngredientWithQuantity());
        return "logged-user/creatingSetOfIngredients";
    }

    @PostMapping("/create-set-of-ingredients")
    public String addingIngredientsToRecipe(@Valid @ModelAttribute("ingredientsWithQuantity") IngredientWithQuantity ingredientWithQuantity, BindingResult result, @ModelAttribute("setId") String setId, Model model) {
        if (result.hasErrors()) {
            return "logged-user/creatingSetOfIngredients";
        }

        IngredientWithQuantity savedIngredientWithQuantity = ingredientWithQuantityService.saveIngredientWithQuantity(ingredientWithQuantity);
        if (!setId.isEmpty()) {
            SetOfIngredientsWithQuantities set = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(Long.parseLong(setId));
            set.addIngredientWithQuantity(savedIngredientWithQuantity);
            setOfIngredientsWithQuantitiesService.updateSetOfIngredientsWithQuantities(set);
            model.addAttribute("setWithIngredients", set);
            return "logged-user/creatingSetOfIngredients";
        }

        SetOfIngredientsWithQuantities setOfIngredientsWithQuantities = new SetOfIngredientsWithQuantities();
        setOfIngredientsWithQuantities.addIngredientWithQuantity(savedIngredientWithQuantity);
        SetOfIngredientsWithQuantities savedSetOfIngredientsWithQuantities = setOfIngredientsWithQuantitiesService.saveSetOfIngredientsWithQuantities(setOfIngredientsWithQuantities);
        model.addAttribute("setWithIngredients", savedSetOfIngredientsWithQuantities);
        return "logged-user/creatingSetOfIngredients";
    }

    @GetMapping("/add")
    public String joinSetOfIngredientsToRecipe(@ModelAttribute("setId") String setId, Model model) {
        SetOfIngredientsWithQuantities setOfIngredients = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(Long.parseLong(setId));
        Recipe recipe = new Recipe();
        recipe.setSetOfIngredientsWithQuantities(setOfIngredients);
        model.addAttribute("setOfIngredients", setOfIngredients);
        model.addAttribute("recipe", recipe);
        return "logged-user/addRecipe";
    }

    @PostMapping("/add")
    public String addRecipe(@Valid Recipe recipe, BindingResult result) {
        if (result.hasErrors()) {
            return "logged-user/addRecipe";
        }
        recipeService.saveRecipe(recipe);
        return "redirect:list";
    }
}
