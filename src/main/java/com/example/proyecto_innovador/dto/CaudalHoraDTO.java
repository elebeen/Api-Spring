package com.example.proyecto_innovador.dto;

import java.time.LocalDateTime;

public class CaudalHoraDTO {
    private Double caudal;
    private LocalDateTime timestamp;

    public CaudalHoraDTO(Double caudal, LocalDateTime timestamp) {
        this.caudal = caudal;
        this.timestamp = timestamp;
    }

    public Double getCaudal() {
        return caudal;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
