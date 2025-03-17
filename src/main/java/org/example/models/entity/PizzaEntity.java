package org.example.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "pizzas")
public class PizzaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "pizza_sizes", joinColumns = @JoinColumn(name = "pizza_id"))
    @MapKeyColumn(name = "size")
    @Column(name = "price")
    private Map<String, Double> sizePrices;

    @Column(nullable = false)
    private boolean isActive = true;
}