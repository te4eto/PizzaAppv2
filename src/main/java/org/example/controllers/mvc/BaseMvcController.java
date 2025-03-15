package org.example.controllers.mvc;

import org.example.models.entity.CartEntity;
import org.example.models.entity.CartItemEntity;
import org.example.models.entity.UserEntity;
import org.example.services.CartService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public abstract class BaseMvcController {


    @Autowired
    protected CartService cartService;

    @Autowired
    protected UserService userService;

    protected void addCommonAttributes(Model model) {
        int cartItemCount = calculateCartItemCount();
        model.addAttribute("cartItemCount", cartItemCount);
    }

    private int calculateCartItemCount() {
        UserEntity user = userService.getCurrentUser();
        if (user == null) {
            return 0;
        }

        try {
            CartEntity cart = cartService.getCart();
            return cart.getCartItems()
                    .stream()
                    .mapToInt(CartItemEntity::getQuantity)
                    .sum();
        } catch (Exception e) {
            return 0;
        }
    }
}