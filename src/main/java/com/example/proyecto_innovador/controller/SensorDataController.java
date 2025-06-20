package com.example.proyecto_innovador.controller;

import com.example.proyecto_innovador.model.FlowDay;
import com.example.proyecto_innovador.model.FlowRaw;
import com.example.proyecto_innovador.model.SensorData;
import com.example.proyecto_innovador.repository.FlowDayRepository;
import com.example.proyecto_innovador.repository.FlowRawRepository;
import com.example.proyecto_innovador.repository.SensorDataRepository;
import com.example.proyecto_innovador.dto.CaudalHoraDTO;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private SensorDataRepository repository;
    @Autowired
    private FlowRawRepository flowRawRepository;
    @Autowired
    private FlowDayRepository flowDayRepository;

    // GET: Obtener los últimos 10 registros por orden de llegada
    @GetMapping("/ultimos")
    public List<Double> obtenerUltimosFlujos() {
        return repository.findTop10ByOrderByTimestampDesc()
                .stream()
                .map(SensorData::getCaudal)
                .collect(Collectors.toList());
    }

    // GET: Obtener los 10 caudales más altos con su timestamp
    @GetMapping("/top10")
    public List<CaudalHoraDTO> obtenerTop10Caudales() {
        return repository.findTop10ByOrderByCaudalDesc()
                .stream()
                .map(d -> new CaudalHoraDTO(d.getCaudal(), d.getTimestamp()))
                .collect(Collectors.toList());  
    }

    @GetMapping("/last/hour")
    public List<FlowRaw> LastHour() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<FlowRaw> lastHourData = flowRawRepository.findByTimestampAfterOrderByTimestampAsc(oneHourAgo);
        return lastHourData;
    }

    @GetMapping("/today/total")
    public FlowDay getTodayFlowDay() {
        Optional<FlowDay> today = flowDayRepository.findTopByOrderByDateDesc();
        return today.orElse(null);
    }

/*************  ✨ Windsurf Command ⭐  *************/
/**
 * Retrieves a list of FlowDay records for the current day.
 *
 * This method queries the FlowDayRepository to get all flow day records 
 * that have a date after the previous day. It effectively filters and 
 * returns records for the current day. The method is exposed as a GET 
 * endpoint at "/api/sensores/today".
 *
 * @return a list of FlowDay instances representing the flow data for the 
 *         current day, or null if no records are found.
 */

/*******  18e6ccf2-db85-4b8f-be80-a23f59ff3275 *******/
    @GetMapping("/today")
    public List<FlowRaw> getTodayFlow() {
        LocalDateTime today = LocalDateTime.now().minusDays(1); // Obtiene la fecha actual del servidor de la API
        List<FlowRaw> flowDays = flowRawRepository.findByTimestampAfterOrderByTimestampAsc(today);
        // Filtra los registros para obtener solo el del día actual
        return flowDays;
    }

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

        return lastMonthData;
    }
}