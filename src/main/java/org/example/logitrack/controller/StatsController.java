package org.example.logitrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.logitrack.dto.DashboardStatsDTO;
import org.example.logitrack.model.Produit;
import org.example.logitrack.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(statsService.getDashboardStats());
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<Produit>> getLowStockProducts(@RequestParam(defaultValue = "5") int threshold) {
        return ResponseEntity.ok(statsService.getLowStockProducts(threshold));
    }
}
