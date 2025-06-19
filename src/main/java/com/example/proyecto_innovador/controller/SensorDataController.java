package com.example.proyecto_innovador.controller;

import com.example.proyecto_innovador.model.FlowRaw;
import com.example.proyecto_innovador.model.SensorData;
import com.example.proyecto_innovador.repository.FlowRawRepository;
import com.example.proyecto_innovador.repository.SensorDataRepository;
import com.example.proyecto_innovador.dto.CaudalHoraDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/sensores")
@CrossOrigin // Permite el acceso desde Android, Node-RED, etc.
public class SensorDataController {

    @Autowired
    private SensorDataRepository repository;
    @Autowired
    private FlowRawRepository flowRawRepository;

    // POST: Recibe datos del ESP32
    // @PostMapping
    // public SensorData recibirDatos(@RequestBody SensorData datos) {
    //     return repository.save(datos);
    // }

    // GET: Obtener todos los datos
    // @GetMapping
    // public List<SensorData> obtenerTodos() {
    //     return repository.findAll();
    // }

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

    @GetMapping("last/hour")
    public List<FlowRaw> LastHour() {
        // Instant oneHourAgoInstant = Instant.now().minusSeconds(3600);
        // LocalDateTime oneHourAgo = LocalDateTime.ofInstant(oneHourAgoInstant, ZoneId.of("UTC")); 
        // List<FlowRaw> lastHourData = flowRawRepository.findByTimestampAfterOrderByTimestampAsc(oneHourAgo);
        // return lastHourData;
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        System.out.println("Timestamp one hour ago: " + oneHourAgo);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current timestamp: " + now);
        return flowRawRepository.findByTimestampAfterOrderByTimestampAsc(oneHourAgo);
    }

    @GetMapping("last/day")
    public String LastDay(@RequestParam String param) {
        return new String();
    }

    @GetMapping("last/month")
    public List<FlowRaw> LastMonth() {
        return flowRawRepository.findByOrderByTimestampAsc();
    }
}
