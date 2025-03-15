package org.example.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzaResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Map<String, Double> sizePrices;
}
