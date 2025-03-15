package org.example.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long pizzaId;
    private String pizzaName;
    private String size;
    private Double price;
    private Integer quantity;
    private Long cartItemId;
}
