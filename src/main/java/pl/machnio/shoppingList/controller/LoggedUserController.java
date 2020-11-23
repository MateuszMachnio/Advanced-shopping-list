package pl.machnio.shoppingList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.machnio.shoppingList.entity.Plan;
import pl.machnio.shoppingList.entity.User;
import pl.machnio.shoppingList.service.PlanService;
import pl.machnio.shoppingList.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/logged-user")
public class LoggedUserController {

    private final PlanService planService;
    private final UserService userService;

    public LoggedUserController(PlanService planService, UserService userService) {
        this.planService = planService;
        this.userService = userService;
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
        User currentUser = userService.getCurrentUser();
        Plan savedPlan = planService.savePlan(plan);
        currentUser.getPlans().add(savedPlan);
        userService.updateUser(currentUser);

        return "redirect:dashboard";
    }

}
