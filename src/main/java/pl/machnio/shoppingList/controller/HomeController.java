package pl.machnio.shoppingList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("")
    public String homePage() {
        return "home";
    }

    @GetMapping("/list")
    @ResponseBody
    public String list() {
        return "list";
    }

    @PostMapping("/login")
    @ResponseBody
    public String test() {
        return "login";
    }

}
