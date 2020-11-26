package pl.machnio.shoppingList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.machnio.shoppingList.entity.IngredientWithQuantity;
import pl.machnio.shoppingList.entity.Recipe;
import pl.machnio.shoppingList.entity.SetOfIngredientsWithQuantities;
import pl.machnio.shoppingList.entity.User;
import pl.machnio.shoppingList.service.IngredientWithQuantityService;
import pl.machnio.shoppingList.service.RecipeService;
import pl.machnio.shoppingList.service.SetOfIngredientsWithQuantitiesService;
import pl.machnio.shoppingList.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/logged-user/recipe")
public class LoggedUserRecipeController {

    private final RecipeService recipeService;
    private final IngredientWithQuantityService ingredientWithQuantityService;
    private final SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService;
    private final UserService userService;

    public LoggedUserRecipeController(RecipeService recipeService, IngredientWithQuantityService ingredientWithQuantityService, SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService, UserService userService) {
        this.recipeService = recipeService;
        this.ingredientWithQuantityService = ingredientWithQuantityService;
        this.setOfIngredientsWithQuantitiesService = setOfIngredientsWithQuantitiesService;
        this.userService = userService;
    }

    @GetMapping("/create-set-of-ingredients")
    public String addIngredientsToRecipe(Model model) {
        model.addAttribute("ingredientsWithQuantity", new IngredientWithQuantity());
        return "logged-user/recipe/creatingSetOfIngredients";
    }

    @PostMapping("/create-set-of-ingredients")
    public String addingIngredientsToRecipe(@Valid @ModelAttribute("ingredientsWithQuantity") IngredientWithQuantity ingredientWithQuantity, BindingResult result, @ModelAttribute("setId") String setId, Model model) {
        if (result.hasErrors()) {
            return "logged-user/recipe/creatingSetOfIngredients";
        }

        IngredientWithQuantity savedIngredientWithQuantity = ingredientWithQuantityService.saveIngredientWithQuantity(ingredientWithQuantity);
        if (!setId.isEmpty()) {
            SetOfIngredientsWithQuantities set = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(Long.parseLong(setId));
            set.addIngredientWithQuantity(savedIngredientWithQuantity);
            setOfIngredientsWithQuantitiesService.updateSetOfIngredientsWithQuantities(set);
            model.addAttribute("setWithIngredients", set);
            return "logged-user/recipe/creatingSetOfIngredients";
        }

        SetOfIngredientsWithQuantities setOfIngredientsWithQuantities = new SetOfIngredientsWithQuantities();
        setOfIngredientsWithQuantities.addIngredientWithQuantity(savedIngredientWithQuantity);
        SetOfIngredientsWithQuantities savedSetOfIngredientsWithQuantities = setOfIngredientsWithQuantitiesService.saveSetOfIngredientsWithQuantities(setOfIngredientsWithQuantities);
        model.addAttribute("setWithIngredients", savedSetOfIngredientsWithQuantities);
        return "logged-user/recipe/creatingSetOfIngredients";
    }

    @GetMapping("/add")
    public String joinSetOfIngredientsToRecipe(@ModelAttribute("setId") String setId, Model model) {
        SetOfIngredientsWithQuantities setOfIngredients = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(Long.parseLong(setId));
        Recipe recipe = new Recipe();
        recipe.setSetOfIngredientsWithQuantities(setOfIngredients);
        model.addAttribute("setOfIngredients", setOfIngredients);
        model.addAttribute("recipe", recipe);
        return "logged-user/recipe/add";
    }

    @PostMapping("/add")
    public String addRecipe(@Valid Recipe recipe, BindingResult result) {
        if (result.hasErrors()) {
            return "logged-user/recipe/add";
        }
        Recipe savedRecipe = recipeService.saveRecipe(recipe);
        User currentUserWithRecipes = userService.getCurrentUserWithRecipes();
        currentUserWithRecipes.addRecipe(savedRecipe);
        userService.updateUser(currentUserWithRecipes);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String recipeList(Model model) {
        model.addAttribute("recipeList", userService.getCurrentUserWithRecipes().getRecipes());
        return "logged-user/recipe/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("setOfIngredients", setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(recipe.getSetOfIngredientsWithQuantities().getId()));
        return "logged-user/recipe/delete";
    }

    @PostMapping("/delete")
    public String deletingRecipe(Recipe recipe) {
        recipeService.deleteRecipe(recipe);
        return "redirect:list";
    }

    @GetMapping("/edit/{id}")
    public String editRecipe(@PathVariable long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("setOfIngredients", setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(recipe.getSetOfIngredientsWithQuantities().getId()));
        return "logged-user/recipe/edit";
    }

    @PostMapping("/delete")
    public String editingRecipe(Recipe recipe) {
        recipeService.updateRecipe(recipe);
        return "redirect:list";
    }

}
