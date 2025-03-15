package org.example.mappers;

import org.example.models.dto.PizzaResponseDTO;
import org.example.models.dto.requstdto.PizzaRequestDTO;
import org.example.models.entity.PizzaEntity;
import org.springframework.stereotype.Component;

@Component
public class PizzaMapper {

    // Request DTO → Entity
    public PizzaEntity toPizzaEntity(PizzaRequestDTO dto) {
        PizzaEntity pizza = new PizzaEntity();
        pizza.setName(dto.getName());
        pizza.setDescription(dto.getDescription());
        pizza.setSizePrices(dto.getSizePrices());
        pizza.setActive(true); // Default for new pizzas
        return pizza;
    }

    // Entity → Response DTO
    public PizzaResponseDTO toPizzaResponseDTO(PizzaEntity pizza) {
        return new PizzaResponseDTO(
                pizza.getId(),
                pizza.getName(),
                pizza.getDescription(),
                pizza.getSizePrices()
        );
    }
}