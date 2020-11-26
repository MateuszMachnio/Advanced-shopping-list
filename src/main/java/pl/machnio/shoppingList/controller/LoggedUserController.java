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
    private final IngredientService ingredientService;

    public LoggedUserController(PlanService planService, UserService userService, RecipeService recipeService, IngredientWithQuantityService ingredientWithQuantityService, SetOfIngredientsWithQuantitiesService setOfIngredientsWithQuantitiesService, IngredientService ingredientService) {
        this.planService = planService;
        this.userService = userService;
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

}
