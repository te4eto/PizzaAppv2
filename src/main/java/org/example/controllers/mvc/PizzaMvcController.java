package org.example.controllers.mvc;

import org.example.models.dto.PizzaResponseDTO;
import org.example.models.dto.requstdto.PizzaRequestDTO;
import org.example.services.CartService;
import org.example.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
public class PizzaMvcController extends BaseMvcController{

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartService cartService;

    @GetMapping("/pizzas")
    public String showPizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllActivePizzas());
        addCommonAttributes(model);
        return "pizzas";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long pizzaId, @RequestParam String size) {
        cartService.addToCart(pizzaId, size, 1);
        return "redirect:/pizzas";
    }

    @PostMapping("/order-now")
    public String orderNow(@RequestParam Long pizzaId, @RequestParam String size) {
        cartService.addToCart(pizzaId, size, 1);
        return "redirect:/cart";
    }

    @GetMapping("/admin/pizzas/add")
    public String showAddPizzaForm(Model model) {
        model.addAttribute("pizza", new PizzaRequestDTO("", "", new HashMap<>()));
        addCommonAttributes(model);
        return "add-pizza";
    }

    @PostMapping("/admin/pizzas/add")
    public String addPizza(@ModelAttribute("pizza") PizzaRequestDTO dto) {
        pizzaService.addPizza(dto);
        return "redirect:/admin/pizzas";
    }

    @PostMapping("/admin/pizzas/deactivate/{id}")
    public String deactivatePizza(@PathVariable Long id) {
        pizzaService.deactivatePizza(id);
        return "redirect:/admin/pizzas";
    }

    @GetMapping("/admin/pizzas")
    public String showManagePizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllActivePizzas());
        addCommonAttributes(model);
        return "manage-pizzas";
    }

    @PostMapping("/admin/pizzas/clear")
    public String clearAllPizzas() {
        List<PizzaResponseDTO> activePizzas = pizzaService.getAllActivePizzas();
        activePizzas.forEach(pizza -> pizzaService.deactivatePizza(pizza.getId()));
        return "redirect:/admin/pizzas";
    }
}