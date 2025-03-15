package org.example.controllers.rest;

import org.example.mappers.PizzaMapper;
import org.example.models.dto.PizzaResponseDTO;
import org.example.models.dto.requstdto.PizzaRequestDTO;
import org.example.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzaMapper pizzaMapper;

    @GetMapping("/pizzas")
    public ResponseEntity<List<PizzaResponseDTO>> getActivePizzas() {
        return ResponseEntity.ok(pizzaService.getAllActivePizzas());
    }

    @PostMapping("/admin/pizzas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PizzaResponseDTO> addPizza(@RequestBody PizzaRequestDTO dto) {
        PizzaResponseDTO response = pizzaService.addPizza(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/pizzas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivatePizza(@PathVariable Long id) {
        pizzaService.deactivatePizza(id);
        return ResponseEntity.noContent().build();
    }
}