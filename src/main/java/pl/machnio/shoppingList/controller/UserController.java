package pl.machnio.shoppingList.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.machnio.shoppingList.entity.User;
import pl.machnio.shoppingList.service.UserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/registration")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("edit", false);
//        model.addAttribute("loggedinuser", getPrincipal());
        return "user/registration";
    }


    @PostMapping("/registration")
    public String saveUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/registration";
        }

        if(userService.findByEmail(user.getEmail()) != null){
            FieldError error = new FieldError("user","email", messageSource.getMessage("non.unique.email", new String[]{user.getEmail()}, Locale.getDefault()));
            result.addError(error);
            return "user/registration";
        }

        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
//        model.addAttribute("loggedinuser", getPrincipal());
        return "user/registrationSuccess";
    }

}
