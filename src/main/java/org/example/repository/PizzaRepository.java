package org.example.repository;

import org.example.models.entity.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {
    public PizzaEntity findByName(String name);

    List<PizzaEntity> findByIsActiveTrue();
}
