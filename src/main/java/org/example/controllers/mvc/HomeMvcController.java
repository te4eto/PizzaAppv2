package org.example.controllers.mvc;

import org.example.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeMvcController extends BaseMvcController {
    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllActivePizzas());
        addCommonAttributes(model);
        return "home";
    }
}