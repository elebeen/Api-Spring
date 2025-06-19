package com.example.proyecto_innovador.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;  
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Flow_day") // Cambia el nombre de la tabla si es necesario
public class FlowDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    @Column(name = "TotalFlow", nullable = false)
    private BigDecimal flowrate;
    private Date date; // Fecha en formato ISO 8601 (ej. "2023-10-01")

    // Constructor por defecto
    public FlowDay() {}
    public FlowDay(BigDecimal flowrate, Date date) {
        this.flowrate = flowrate;
        this.date = date;
    }
    // Getters y setters
    public BigDecimal getFlowrate() {
        return flowrate;
    }
    public void setFlowrate(BigDecimal flowrate) {
        this.flowrate = flowrate;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}

