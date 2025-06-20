package com.example.proyecto_innovador.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyecto_innovador.model.FlowRaw;

public interface FlowRawRepository extends JpaRepository<FlowRaw, Long> {
    List<FlowRaw> findByTimestampAfterOrderByTimestampAsc(LocalDateTime timestamp);
}
