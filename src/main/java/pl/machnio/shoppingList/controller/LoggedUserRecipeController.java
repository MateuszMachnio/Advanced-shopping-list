package pl.machnio.shoppingList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.machnio.shoppingList.entity.*;
import pl.machnio.shoppingList.service.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/logged-user/recipe")
public class LoggedUserRecipeController {

    private final RecipeService recipeService;
    private final IngredientWithQuantityService ingredientWithQuantityService;
    private final SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService;
    private final UserService userService;
    private final IngredientService ingredientService;

    public LoggedUserRecipeController(RecipeService recipeService, IngredientWithQuantityService ingredientWithQuantityService, SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService, UserService userService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientWithQuantityService = ingredientWithQuantityService;
        this.setOfIngredientsWithQuantitiesService = setOfIngredientsWithQuantitiesService;
        this.userService = userService;
        this.ingredientService = ingredientService;
    }

    @ModelAttribute("ingredients")
    public List<Ingredient> getIngredients() {
        return ingredientService.findAllIngredients();
    }

    @GetMapping("/create-set-of-ingredients")
    public String addIngredientsToRecipe(Model model) {
        model.addAttribute("ingredientsWithQuantity", new IngredientWithQuantity());
        return "logged-user/recipe/creatingSetOfIngredients";
    }

    @PostMapping("/create-set-of-ingredients")
    public String addingIngredientsToRecipe(@Valid @ModelAttribute("ingredientsWithQuantity") IngredientWithQuantity ingredientWithQuantity, BindingResult result, @ModelAttribute("setId") long setId, Model model) {
        if (result.hasErrors()) {
            if (setId > 0) {
                model.addAttribute("setWithIngredients", setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(setId));
            }
            return "logged-user/recipe/creatingSetOfIngredients";
        }

        IngredientWithQuantity savedIngredientWithQuantity = ingredientWithQuantityService.saveIngredientWithQuantity(ingredientWithQuantity);
        if (setId > 0) {
            SetOfIngredientsWithQuantities set = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(setId);
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
    public String joinSetOfIngredientsToRecipe(@ModelAttribute("setId") long setId, Model model) {
        SetOfIngredientsWithQuantities setOfIngredients = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(setId);
        Recipe recipe = new Recipe();
//        recipe.setSetOfIngredientsWithQuantities(setOfIngredients);
        model.addAttribute("setOfIngredients", setOfIngredients);
        model.addAttribute("recipe", recipe);
        return "logged-user/recipe/add";
    }

    @PostMapping("/add")
    public String addRecipe(@Valid Recipe recipe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("setOfIngredients", setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(recipe.getSetOfIngredientsWithQuantities().getId()));
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
        User currentUserWithRecipes = userService.getCurrentUserWithRecipes();
        currentUserWithRecipes.removeRecipe(recipe);
        userService.updateUser(currentUserWithRecipes);
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

    @PostMapping("/edit")
    public String editingRecipe(@Valid Recipe recipe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("setOfIngredients", setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(recipe.getSetOfIngredientsWithQuantities().getId()));
            return "logged-user/recipe/edit";
        }
        recipeService.updateRecipe(recipe);
        return "redirect:list";
    }

    @GetMapping("/edit-set-of-ingredients")
    public String editSetOfIngredients(@ModelAttribute("setOfIngredientsId") long setId, @ModelAttribute("recipeId") long recipeId,  Model model) {
        model.addAttribute("setOfIngredients", setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(setId));
        model.addAttribute("recipeId", recipeId);
        return "logged-user/recipe/editSetOfIngredients";
    }

    @GetMapping("/edit-ingredient/{recipeId}/{setId}/{iwqId}")
    public String editIngredient(@PathVariable long recipeId, @PathVariable long setId, @PathVariable long iwqId, Model model) {
        IngredientWithQuantity ingredientWithQuantity = ingredientWithQuantityService.findById(iwqId);
        model.addAttribute("ingredientWithQuantity", ingredientWithQuantity);
        model.addAttribute("ingredientId", ingredientWithQuantity.getIngredient().getId());
        model.addAttribute("ingredientName", ingredientWithQuantity.getIngredient().getName());
        model.addAttribute("setId", setId);
        model.addAttribute("recipeId", recipeId);
        return "/logged-user/recipe/editIngredientWithQuantity";
    }

    @PostMapping("/editing-ingredient")
    public String editIngredient(@Valid @ModelAttribute("ingredientWithQuantity") IngredientWithQuantity ingredientWithQuantity, BindingResult result, @ModelAttribute("setId") long setId, @ModelAttribute("recipeId") long recipeId, Model model) {
        if (result.hasErrors()) {
            return "/logged-user/recipe/editIngredientWithQuantity";
        }
        ingredientWithQuantityService.updateIngredientWithQuantity(ingredientWithQuantity);
        model.addAttribute("setOfIngredients", setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(setId));
        model.addAttribute("recipeId", recipeId);
        return "logged-user/recipe/editSetOfIngredients";
    }

    @GetMapping("/delete-ingredient/{recipeId}/{setId}/{iwqId}")
    public String deleteIngredient(@PathVariable long recipeId, @PathVariable long setId, @PathVariable long iwqId, Model model) {
        SetOfIngredientsWithQuantities set = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(setId);
        set.removeIngredientWithQuantity(ingredientWithQuantityService.findById(iwqId));
        setOfIngredientsWithQuantitiesService.updateSetOfIngredientsWithQuantities(set);
        ingredientWithQuantityService.deleteIngredientWithQuantityById(iwqId);
        model.addAttribute("setOfIngredients", set);
        model.addAttribute("recipeId", recipeId);
        return "/logged-user/recipe/editSetOfIngredients";
    }

    @GetMapping("/add-ingredient/{recipeId}/{setId}")
    public String addIngredient(@PathVariable long recipeId, @PathVariable long setId, Model model) {
        model.addAttribute("ingredientWithQuantity", new IngredientWithQuantity());
        model.addAttribute("setId", setId);
        model.addAttribute("recipeId", recipeId);
        return "/logged-user/recipe/addIngredientToSet";
    }

    @PostMapping("/adding-ingredient")
    public String addingIngredient(@Valid @ModelAttribute("ingredientWithQuantity") IngredientWithQuantity ingredientWithQuantity, BindingResult result, @ModelAttribute("setId") long setId, @ModelAttribute("recipeId") long recipeId, Model model) {
        if (result.hasErrors()) {
            return "/logged-user/recipe/addIngredientToSet";
        }
        IngredientWithQuantity savedIngredientWithQuantity = ingredientWithQuantityService.saveIngredientWithQuantity(ingredientWithQuantity);
        SetOfIngredientsWithQuantities set = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(setId);
        set.addIngredientWithQuantity(savedIngredientWithQuantity);
        setOfIngredientsWithQuantitiesService.updateSetOfIngredientsWithQuantities(set);
        model.addAttribute("setOfIngredients", set);
        model.addAttribute("recipeId", recipeId);
        return "logged-user/recipe/editSetOfIngredients";
    }

}
