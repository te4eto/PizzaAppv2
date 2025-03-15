package org.example.controllers.mvc;

import org.example.mappers.OrderMapper;
import org.example.models.dto.OrderDTO;
import org.example.services.CartService;
import org.example.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderMvcController extends BaseMvcController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public String showOrderHistory(Model model) {
        List<OrderDTO> history = orderService.getOrderHistory()
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());

        model.addAttribute("orders", history);
        addCommonAttributes(model);
        return "orders";
    }
}