package com.example.proyecto_innovador.repository;

import com.example.proyecto_innovador.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findTop10ByOrderByTimestampDesc(); // últimos por hora
    List<SensorData> findTop10ByOrderByCaudalDesc();    // top 10 caudales
}
