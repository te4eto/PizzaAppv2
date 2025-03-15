package org.example.models.dto.requstdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CartItemRequestDTO {
    private Long pizzaId;
    private String size;
    private int quantity;
}
