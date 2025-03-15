package org.example.mappers;

import org.example.models.entity.CartItemEntity;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderItemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemEntity toOrderItemEntity(CartItemEntity cartItem, OrderEntity order) {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrder(order);
        orderItem.setPizzaName(cartItem.getPizza().getName());
        orderItem.setSize(cartItem.getSize());
        double price = cartItem.getPizza().getSizePrices().get(cartItem.getSize());
        orderItem.setPrice(price);
        orderItem.setQuantity(cartItem.getQuantity());

        return orderItem;
    }
}