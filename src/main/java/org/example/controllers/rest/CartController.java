package org.example.controllers.rest;

import org.example.mappers.CartMapper;
import org.example.models.dto.CartResponseDTO;
import org.example.models.dto.requstdto.CartItemRequestDTO;
import org.example.models.entity.CartEntity;
import org.example.services.CartService;
import org.example.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartMapper cartMapper;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestBody CartItemRequestDTO request) {
        cartService.addToCart(request.getPizzaId(), request.getSize(), request.getQuantity());
        CartEntity cart = cartService.getCart();

        return ResponseEntity.ok(convertToCartDTO(cart));
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartResponseDTO> removeFromCart(@PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        CartEntity cart = cartService.getCart();

        return ResponseEntity.ok(convertToCartDTO(cart));
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() {
        CartEntity cart = cartService.getCart();
        return ResponseEntity.ok(convertToCartDTO(cart));
    }

    @PostMapping("/clear")
    public ResponseEntity<CartResponseDTO> clearCart() {
        cartService.clearCart();
        CartEntity cart = cartService.getCart();

        return ResponseEntity.ok(convertToCartDTO(cart));
    }

    private CartResponseDTO convertToCartDTO(CartEntity cart) {
        return cartMapper.toCartDTO(cart);
    }
}