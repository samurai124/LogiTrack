package org.example.logitrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.logitrack.dto.DashboardStatsDTO;
import org.example.logitrack.model.Produit;
import org.example.logitrack.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(statsService.getDashboardStats());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Produit>> getLowStockProducts(@RequestParam(defaultValue = "5") int threshold) {
        return ResponseEntity.ok(statsService.getLowStockProducts(threshold));
    }
}
