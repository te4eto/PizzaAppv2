package org.example.mappers;

import org.example.models.dto.OrderDTO;
import org.example.models.dto.OrderItemDTO;
import org.example.models.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toOrderDTO(OrderEntity order) {
        List<OrderItemDTO> items = order.getOrderItems()
                .stream()
                .map(oi -> new OrderItemDTO(null, oi.getPizzaName(), oi.getSize(), oi.getPrice(), oi.getQuantity()))
                .collect(Collectors.toList());

        double totalPrice = items
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        return new OrderDTO(order.getId(), order.getUser().getId(), items, totalPrice, order.getOrderDate());
    }
}
