package com.example.proyecto_innovador.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyecto_innovador.model.FlowRaw;

public interface FlowRawRepository extends JpaRepository<FlowRaw, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para obtener datos por fecha o caudal específico
    // List<FlowRaw> findByTimestamp(String timestamp);
    // List<FlowRaw> findByFlowRateGreaterThan(Double flowRate);
    List<FlowRaw> findByOrderByTimestampAsc(); // últimos por hora
    List<FlowRaw> findByTimestampAfterOrderByTimestampAsc(LocalDateTime timestamp);
}
