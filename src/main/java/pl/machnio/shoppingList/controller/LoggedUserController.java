package pl.machnio.shoppingList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.machnio.shoppingList.entity.*;
import pl.machnio.shoppingList.service.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/logged-user")
public class LoggedUserController {

    private final PlanService planService;
    private final UserService userService;
    private final RecipeService recipeService;
    private final IngredientWithQuantityService ingredientWithQuantityService;
    private final SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService;
    private final IngredientService ingredientService;

    public LoggedUserController(PlanService planService, UserService userService, RecipeService recipeService, IngredientWithQuantityService ingredientWithQuantityService, SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService, IngredientService ingredientService) {
        this.planService = planService;
        this.userService = userService;
        this.recipeService = recipeService;
        this.ingredientWithQuantityService = ingredientWithQuantityService;
        this.setOfIngredientsWithQuantitiesService = setOfIngredientsWithQuantitiesService;
        this.ingredientService = ingredientService;
    }

    @ModelAttribute("ingredients")
    public List<Ingredient> getIngredients() {
        return ingredientService.findAllIngredients();
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "logged-user/dashboard";
    }

    @GetMapping("/add-plan")
    public String addPlan(Model model) {
        model.addAttribute("plan", new Plan());
        return "logged-user/addPlan";
    }

    @PostMapping("/add-plan")
    public String addingPlan(@Valid Plan plan, BindingResult result) {
        if (result.hasErrors()) {
            return "logged-user/addPlan";
        }
        User currentUser = userService.getCurrentUserWithPlans();
        Plan savedPlan = planService.savePlan(plan);
        currentUser.addPlan(savedPlan);
        userService.updateUser(currentUser);

        return "redirect:dashboard";
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

//    @GetMapping("/add-next-ingredient-to-set")
//    public String addNextIngredientsToRecipe(Model model) {
//        if (model.containsAttribute("setWithIngredients")) {
//            model.addAttribute("ingredientsWithQuantity", new IngredientWithQuantity());
//            return "logged-user/creatingSetOfIngredients";
//        }
//        return "redirect:create-set-of-ingredients";
//    }
//    @PostMapping("/add-next-ingredient-to-set")
//    public String addingNextIngredientsToRecipe(@Valid @ModelAttribute("ingredientsWithQuantity") IngredientWithQuantity ingredientWithQuantity, BindingResult result, @ModelAttribute("setId") String setId, Model model) {
//        if (result.hasErrors()) {
//            return "logged-user/creatingSetOfIngredients";
//        }
//        IngredientWithQuantity savedIngredientWithQuantity = ingredientWithQuantityService.saveIngredientWithQuantity(ingredientWithQuantity);
//        SetOfIngredientsWithQuantities set = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(Long.parseLong(setId));
//        set.addIngredientWithQuantity(savedIngredientWithQuantity);
//        setOfIngredientsWithQuantitiesService.updateSetOfIngredientsWithQuantities(set);
//        model.addAttribute("setWithIngredients", set);
//        return "logged-user/creatingSetOfIngredients";
//    }

    @GetMapping("/create-recipe")
    public String joinSetOfIngredientsToRecipe(@ModelAttribute("setId") String setId, Model model) {
        SetOfIngredientsWithQuantities setOfIngredients = setOfIngredientsWithQuantitiesService.findByIdWithSetOfIngredientsWithQuantity(Long.parseLong(setId));
        Recipe recipe = new Recipe();
        recipe.setSetOfIngredientsWithQuantities(setOfIngredients);
        model.addAttribute("setOfIngredients", setOfIngredients);
        model.addAttribute("recipe", recipe);
        return "logged-user/addRecipe";
    }

    @PostMapping("/create-recipe")
    public String addRecipe(@Valid Recipe recipe, BindingResult result) {
        if (result.hasErrors()) {
            return "logged-user/addRecipe";
        }
        recipeService.saveRecipe(recipe);
        return "redirect:/recipe/list";
    }

}
