package com.example.proyecto_innovador.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flujo_agua")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double caudal;

    private LocalDateTime timestamp;

    // Agrega esto para asignar timestamp automáticamente
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCaudal() {
        return caudal;
    }

    public void setCaudal(Double caudal) {
        this.caudal = caudal;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
