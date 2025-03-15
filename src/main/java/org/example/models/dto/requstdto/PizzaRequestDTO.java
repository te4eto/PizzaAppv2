package org.example.models.dto.requstdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class PizzaRequestDTO {
    private String name;
    private String description;
    private Map<String, Double> sizePrices;
}
