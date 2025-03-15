package org.example.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.mappers.PizzaMapper;
import org.example.models.dto.PizzaResponseDTO;
import org.example.models.dto.requstdto.PizzaRequestDTO;
import org.example.models.entity.PizzaEntity;
import org.example.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaMapper pizzaMapper;

//    public List<PizzaEntity> getAllActivePizzas() {
//        return pizzaRepository.findByIsActiveTrue();
//    }

    public List<PizzaResponseDTO> getAllActivePizzas(){
        var pizzaEntities = pizzaRepository.findByIsActiveTrue();
        List<PizzaResponseDTO> pizzaResponseDTOS = pizzaEntities
                .stream()
                .map(pizzaMapper::toPizzaResponseDTO)
                .toList();

        return pizzaResponseDTOS;
    }

    public PizzaEntity getPizzaById(Long id, boolean requireActive) {
        Optional<PizzaEntity> pizza = pizzaRepository.findById(id);
        if (requireActive) {
            return pizza.filter(PizzaEntity::isActive)
                    .orElseThrow(() -> new EntityNotFoundException("Pizza not found or inactive"));
        }

        return pizza.orElseThrow(() -> new EntityNotFoundException("Pizza not found"));
    }

    public PizzaEntity getPizzaById(Long id) {
        return getPizzaById(id, true);
    }

    public PizzaResponseDTO addPizza(PizzaRequestDTO dto) {
        PizzaEntity pizza = pizzaMapper.toPizzaEntity(dto);
        PizzaEntity savedPizza = pizzaRepository.save(pizza);
        return pizzaMapper.toPizzaResponseDTO(savedPizza);
    }

    public void deactivatePizza(Long id) {
        PizzaEntity pizza = getPizzaById(id, true);
        pizza.setActive(false);
        pizzaRepository.save(pizza);
    }
}