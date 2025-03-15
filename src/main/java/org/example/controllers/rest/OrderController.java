package org.example.controllers.rest;

import org.example.mappers.OrderMapper;
import org.example.models.dto.OrderDTO;
import org.example.models.entity.OrderEntity;
import org.example.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder() {
        OrderEntity order = orderService.placeOrder();
        return ResponseEntity.ok(orderMapper.toOrderDTO(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrderHistory() {
        List<OrderDTO> history = orderService.getOrderHistory()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(history);
    }
}