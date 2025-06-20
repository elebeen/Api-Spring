package com.example.proyecto_innovador.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyecto_innovador.model.FlowDay;

public interface FlowDayRepository extends JpaRepository<FlowDay, Long> {
    Optional<FlowDay> findTopByOrderByDateDesc();
    Optional<FlowDay> findByDate(LocalDateTime date);
    List<FlowDay> findByDateAfterOrderByDateAsc(LocalDateTime date);
}