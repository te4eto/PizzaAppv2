package org.example.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizza_id")
    private PizzaEntity pizza;

    @Column(nullable = false)
    private String pizzaName;

    @Column(nullable = false)
    private String size; // e.g., "small", "medium", "large"

    @Column(nullable = false)
    private Double price; // Price at the time of order

    @Column(nullable = false)
    private Integer quantity;
}