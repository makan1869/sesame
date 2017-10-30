package ir.serenade.sesame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ThymeleafController {
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String update() {
        return "redirect:/";
    }
}