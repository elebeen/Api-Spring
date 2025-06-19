package com.example.proyecto_innovador.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    @Entity
    @Table(name = "Flow_raw") // Cambia el nombre de la tabla si es necesario
    public class FlowRaw {
        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private Long id; // ID único para cada registro
        @Column(name = "flow", nullable = false)
        private Double flowRate; // Caudal en litros por minuto
        private LocalDateTime timestamp; // Timestamp en formato ISO 8601 (ej. "2023-10-01T12:00:00Z")

        public FlowRaw() {}

        public FlowRaw(Double flowRate, LocalDateTime timestamp) {
            this.flowRate = flowRate;
            this.timestamp = timestamp;
        }

        public Double getFlowRate() {
            return flowRate;
        }
        public void setFlowRate(Double flowRate) {
            this.flowRate = flowRate;
        }
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
    }
