package org.example.mappers;

import org.example.models.entity.CartEntity;
import org.example.models.entity.CartItemEntity;
import org.example.models.entity.PizzaEntity;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemEntity toCartItemEntity(CartEntity cart, PizzaEntity pizza, String size, int quantity) {
        CartItemEntity item = new CartItemEntity();
        item.setCart(cart);
        item.setPizza(pizza);
        item.setSize(size);
        item.setQuantity(quantity);
        return item;
    }

    public void updateQuantity(CartItemEntity item, int newQuantity) {
        item.setQuantity(newQuantity);
    }
}