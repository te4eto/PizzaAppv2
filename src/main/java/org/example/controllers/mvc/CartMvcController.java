package org.example.controllers.mvc;

import org.example.mappers.CartMapper;
import org.example.models.dto.CartResponseDTO;
import org.example.models.entity.CartEntity;
import org.example.services.CartService;
import org.example.services.OrderService;
import org.example.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartMvcController extends BaseMvcController{

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartMapper cartMapper;

    @GetMapping
    public String showCart(Model model) {
        CartEntity cart = cartService.getCart();
        CartResponseDTO cartDTO = cartMapper.toCartDTO(cart);
        model.addAttribute("cart", cartDTO);
        addCommonAttributes(model);
        return "cart";
    }

    @PostMapping("/increment/{cartItemId}")
    public String incrementQuantity(@PathVariable Long cartItemId) {
        cartService.incrementQuantity(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/decrement/{cartItemId}")
    public String decrementQuantity(@PathVariable Long cartItemId) {
        cartService.decrementQuantity(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/place-order")
    public String placeOrder(Model model) {
        CartEntity cart = cartService.getCart();
        boolean hasInactiveItems = cart.getCartItems()
                .stream()
                .anyMatch(ci -> !pizzaService.getPizzaById(ci.getPizza().getId(), false).isActive());

        if (hasInactiveItems) {
            model.addAttribute("error", "Cannot place order: some items are unavailable.");
            return showCart(model);
        }

        orderService.placeOrder();
        return "redirect:/orders";
    }

    @PostMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}