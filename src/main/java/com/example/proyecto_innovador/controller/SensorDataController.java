package com.example.proyecto_innovador.controller;

import com.example.proyecto_innovador.model.SensorData;
import com.example.proyecto_innovador.repository.SensorDataRepository;
import com.example.proyecto_innovador.dto.CaudalHoraDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensores")
@CrossOrigin // Permite el acceso desde Android, Node-RED, etc.
public class SensorDataController {

    private final SensorDataRepository repository;

    public SensorDataController(SensorDataRepository repository) {
        this.repository = repository;
    }

    // POST: Recibe datos del ESP32
    @PostMapping
    public SensorData recibirDatos(@RequestBody SensorData datos) {
        return repository.save(datos);
    }

    // GET: Obtener todos los datos
    @GetMapping
    public List<SensorData> obtenerTodos() {
        return repository.findAll();
    }

    // GET: Obtener los últimos 10 registros por orden de llegada
    @GetMapping("/ultimos")
    public List<Double> obtenerUltimosFlujos() {
        return repository.findTop10ByOrderByTimestampDesc()
                .stream()
                .map(SensorData::getCaudal)
                .collect(Collectors.toList());
    }

    // ✅ GET: Obtener los 10 caudales más altos con su timestamp
    @GetMapping("/top10")
    public List<CaudalHoraDTO> obtenerTop10Caudales() {
        return repository.findTop10ByOrderByCaudalDesc()
                .stream()
                .map(d -> new CaudalHoraDTO(d.getCaudal(), d.getTimestamp()))
                .collect(Collectors.toList());
    }
}
