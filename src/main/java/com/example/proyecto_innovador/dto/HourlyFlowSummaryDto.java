package com.example.proyecto_innovador.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HourlyFlowSummaryDto {
    private LocalDateTime minuteTimestamp; // Representa el inicio del minuto
    private Double totalFlowInMinute; // La suma del flujo para ese minuto

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Modified constructor to accept String for timestamp
    public HourlyFlowSummaryDto(String minuteTimestampStr, Double totalFlowInMinute) {
        // Parse the String into LocalDateTime using the defined format
        this.minuteTimestamp = LocalDateTime.parse(minuteTimestampStr, FORMATTER);
        this.totalFlowInMinute = totalFlowInMinute;
    }
    // Getters y Setters
    public LocalDateTime getMinuteTimestamp() {
        return minuteTimestamp;
    }

    public void setMinuteTimestamp(LocalDateTime minuteTimestamp) {
        this.minuteTimestamp = minuteTimestamp;
    }

    public Double getTotalFlowInMinute() {
        return totalFlowInMinute;
    }

    public void setTotalFlowInMinute(Double totalFlowInMinute) {
        this.totalFlowInMinute = totalFlowInMinute;
    }
}