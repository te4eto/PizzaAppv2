package org.example.mappers;

import org.example.models.dto.CartResponseDTO;
import org.example.models.dto.CartItemDTO;
import org.example.models.entity.CartEntity;
import org.example.models.entity.PizzaEntity;
import org.example.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    @Autowired
    private PizzaService pizzaService;

    public CartResponseDTO toCartDTO(CartEntity cart) {
        List<CartItemDTO> items = cart.getCartItems().stream()
                .map(ci -> {
                    PizzaEntity pizza = pizzaService.getPizzaById(ci.getPizza().getId(), false);
                    String name = pizza.isActive() ? pizza.getName() : pizza.getName() + " (Unavailable)";
                    Double price = pizza.isActive() ? pizza.getSizePrices().get(ci.getSize()) : 0.0;
                    return new CartItemDTO(ci.getPizza().getId(), name, ci.getSize(), price, ci.getQuantity(), ci.getId());
                })
                .collect(Collectors.toList());

        double totalPrice = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        return new CartResponseDTO(cart.getId(), cart.getUser() != null ? cart.getUser().getId() : null, items, totalPrice);
    }
}