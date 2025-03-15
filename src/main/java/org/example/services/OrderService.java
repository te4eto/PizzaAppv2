package org.example.services;

import org.example.mappers.OrderItemMapper;
import org.example.models.entity.*;
import org.example.repository.CartItemRepository;
import org.example.repository.CartRepository;
import org.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Transactional
    public OrderEntity placeOrder() {
        CartEntity cart = cartService.getCart();

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        logger.debug("Placing order, cartItems size before = {}", cart.getCartItems().size());

        OrderEntity order = new OrderEntity();
        order.setUser(userService.getCurrentUser());
        order.setOrderDate(LocalDateTime.now());

        List<OrderItemEntity> orderItems = cart.getCartItems()
                .stream()
                .map(cartItem -> orderItemMapper.toOrderItemEntity(cartItem, order))
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        OrderEntity savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();

        cartRepository.saveAndFlush(cart);
        logger.debug("Cart cleared, cartItems size after = {}", cart.getCartItems().size());

        return savedOrder;
    }

    public List<OrderEntity> getOrderHistory() {
        UserEntity user = userService.getCurrentUser();
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
}
