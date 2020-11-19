package pl.machnio.shoppingList.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("")
    public String homePage() {
        return "home";
    }

    @GetMapping("/some")
    @ResponseBody
    public String test() {
        return "some";
    }

}