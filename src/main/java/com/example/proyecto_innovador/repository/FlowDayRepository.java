package com.example.proyecto_innovador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyecto_innovador.model.FlowDay;

public interface FlowDayRepository extends JpaRepository<FlowDay, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para obtener datos por fecha o caudal específico
    // List<FlowDay> findByDate(Date date);
    // List<FlowDay> findByFlowrateGreaterThan(Double flowrate);
    
}
