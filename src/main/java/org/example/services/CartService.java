package org.example.services;

import org.example.mappers.CartItemMapper;
import org.example.models.entity.CartEntity;
import org.example.models.entity.CartItemEntity;
import org.example.models.entity.PizzaEntity;
import org.example.models.entity.UserEntity;
import org.example.repository.CartItemRepository;
import org.example.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Transactional
    public void addToCart(Long pizzaId, String size, int quantity) {
        CartEntity cart = getOrCreateCart();
        PizzaEntity pizza = pizzaService.getPizzaById(pizzaId);

        Optional<CartItemEntity> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getPizza().getId().equals(pizzaId) && item.getSize().equals(size))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemEntity item = existingItem.get();
            cartItemMapper.updateQuantity(item, item.getQuantity() + quantity);
        } else {
            CartItemEntity item = cartItemMapper.toCartItemEntity(cart, pizza, size, quantity);
            cart.getCartItems().add(item);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public CartEntity getOrCreateCart() {
        UserEntity user = userService.getCurrentUser();

        if (user == null) {
            CartEntity cart = new CartEntity();
            cart.setCartItems(new ArrayList<>());
            return cart;
        }

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartEntity cart = new CartEntity();
                    cart.setUser(user);
                    cart.setCartItems(new ArrayList<>());
                    return cartRepository.save(cart);
                });
    }

    @Transactional
    public void incrementQuantity(Long cartItemId) {
        CartEntity cart = getOrCreateCart();
        CartItemEntity item = cart.getCartItems().stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        cartItemMapper.updateQuantity(item, item.getQuantity() + 1);
        cartRepository.saveAndFlush(cart);
    }

    @Transactional
    public void decrementQuantity(Long cartItemId) {
        CartEntity cart = getOrCreateCart();
        CartItemEntity item = cart.getCartItems().stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        if (item.getQuantity() > 1) {
            cartItemMapper.updateQuantity(item, item.getQuantity() - 1);
            cartRepository.saveAndFlush(cart);
        } else {
            cart.getCartItems().remove(item);
            cartItemRepository.delete(item);
            cartRepository.saveAndFlush(cart);
        }
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        CartEntity cart = getOrCreateCart();
        Optional<CartItemEntity> itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst();

        itemToRemove.ifPresent(cartItem -> {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        });

        cartRepository.saveAndFlush(cart);
    }

    @Transactional
    public void clearCart() {
        CartEntity cart = getOrCreateCart();
        clearCart(cart);
    }

    @Transactional
    public void clearCart(CartEntity cart){
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.saveAndFlush(cart);
    }

    public CartEntity getCart() {
        return getOrCreateCart();
    }
}