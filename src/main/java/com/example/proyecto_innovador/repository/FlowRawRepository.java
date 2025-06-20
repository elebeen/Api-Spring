package com.example.proyecto_innovador.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.proyecto_innovador.dto.HourlyFlowSummaryDto;
import com.example.proyecto_innovador.model.FlowRaw;

public interface FlowRawRepository extends JpaRepository<FlowRaw, Long> {
    List<FlowRaw> findByTimestampAfterOrderByTimestampAsc(LocalDateTime timestamp);

    @Query(
        "SELECT new com.example.proyecto_innovador.dto.HourlyFlowSummaryDto(" +
        "  CAST(FUNCTION('DATE_FORMAT', fr.timestamp, '%Y-%m-%d %H:%i:00') AS string), " +
        "  SUM(fr.flowRate)" +
        ") " +
        "FROM FlowRaw fr " +
        "WHERE fr.timestamp >= :startTime " +
        "GROUP BY FUNCTION('DATE_FORMAT', fr.timestamp, '%Y-%m-%d %H:%i:00') " +
        "ORDER BY FUNCTION('DATE_FORMAT', fr.timestamp, '%Y-%m-%d %H:%i:00') ASC"
)
    List<HourlyFlowSummaryDto> findHourlyFlowSummary(@Param("startTime") LocalDateTime startTime);

}
