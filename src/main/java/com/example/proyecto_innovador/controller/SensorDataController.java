package com.example.proyecto_innovador.controller;

import com.example.proyecto_innovador.dto.HourlyFlowSummaryDto;
import com.example.proyecto_innovador.model.FlowDay;
import com.example.proyecto_innovador.model.FlowRaw;
import com.example.proyecto_innovador.repository.FlowDayRepository;
import com.example.proyecto_innovador.repository.FlowRawRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensores")
@CrossOrigin // Permite el acceso desde Android, Node-RED, etc.
public class SensorDataController {

    @Autowired
    private FlowRawRepository flowRawRepository;
    @Autowired
    private FlowDayRepository flowDayRepository;
    // Devuelve una lista de FlowRaw que contiene los datos de flujo en bruto
    @GetMapping("/last/hour")
    public List<FlowRaw> LastHour() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<FlowRaw> lastHourData = flowRawRepository.findByTimestampAfterOrderByTimestampAsc(oneHourAgo);

        if (lastHourData.isEmpty()) {
            return null; // Si no hay registros, retorna null
        }
        return lastHourData;
         // Calcula la hora de inicio (hace 1 hora), asegurando que sea en UTC
        // Instant oneHourAgoInstant = Instant.now().minusSeconds(3600);
        // LocalDateTime oneHourAgo = LocalDateTime.ofInstant(oneHourAgoInstant, ZoneId.of("UTC")); 

        // // Llama al nuevo método del repositorio para obtener los datos agregados
        // List<HourlyFlowSummaryDto> hourlySummary = flowRawRepository.findHourlyFlowSummary(oneHourAgo);

        // Si necesitas asegurar exactamente 60 elementos, puedes "rellenar" los minutos faltantes
        // con 0 en Java si la consulta no devuelve datos para todos los minutos.
        // Pero la consulta normalmente devuelve solo los minutos con datos.

        // return hourlySummary;
    }

    // Devuelve solo un dato que representa la suma de todos los datos de flujo del día actual
    @GetMapping("/today/total")
    public Optional<FlowDay> getTodayFlowDay() {
        Optional<FlowDay> today = flowDayRepository.findTopByOrderByDateDesc();
        if (today.isEmpty()) {
            return Optional.empty();
        }
        return today;
    }

    // Devuelve una lista de FlowRaw que contiene los datos de flujo del día actual
    @GetMapping("/today")
    public List<FlowRaw> getTodayFlow() {
        LocalDateTime today = LocalDateTime.now().minusDays(1); // Obtiene la fecha actual del servidor de la API
        List<FlowRaw> flowDays = flowRawRepository.findByTimestampAfterOrderByTimestampAsc(today);
        // Filtra los registros para obtener solo el del día actual
        if (flowDays.isEmpty()) {
            return null; // Si no hay registros, retorna null
        }
        return flowDays;
    }

    // Devuelve una lista de FlowDay que contiene los datos de flujo del mes actual en dias
    @GetMapping("/last/30")
    public List<FlowDay> getLastMonthDailyFlow() {
        // Calcula el primer día del mes actual
        LocalDateTime firstDayOfCurrentMonth = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());

        // Calcula el primer día del mes anterior
        LocalDateTime firstDayOfLastMonth = firstDayOfCurrentMonth.minusMonths(1);

        // Obtiene todos los registros desde el primer día del mes anterior hasta hoy
        // Esto incluye el mes anterior y los días transcurridos del mes actual.
        List<FlowDay> lastMonthData = flowDayRepository.findByDateAfterOrderByDateAsc(firstDayOfLastMonth.minusDays(1)); // Restamos un día para incluir el primer día del mes anterior
        // Alternativamente, si quieres EXACTAMENTE el mes anterior (ej. del 1 de Mayo al 31 de Mayo),
        // usarías findByDateBetween(firstDayOfLastMonth, firstDayOfCurrentMonth.minusDays(1))
        // y necesitarías un método findByDateBetween en tu repositorio.
        if (lastMonthData.isEmpty()) {
            return null; // Si no hay registros, retorna null
        }
        return lastMonthData;
    }
}