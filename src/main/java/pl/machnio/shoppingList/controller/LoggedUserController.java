package pl.machnio.shoppingList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.machnio.shoppingList.entity.Plan;

@Controller
@RequestMapping("/logged_user")
public class LoggedUserController {

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "logged-user/dashboard";
    }

    @GetMapping("/add-plan")
    public String addPlan(Model model) {
        model.addAttribute("plan", new Plan());
        return "logged-user/addPlan";
    }

}
