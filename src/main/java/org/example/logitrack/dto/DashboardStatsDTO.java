package org.example.logitrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.logitrack.model.Commande;
import org.example.logitrack.model.Produit;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private long totalClients;
    private long totalProduits;
    private long totalCommandes;
    private long commandesEnAttente;
    private long commandesExpediees;
    private long commandesLivrees;
    private long stockFaibleCount;
    private List<Produit> produitsStockFaible;
    private Produit produitLePlusCommande;
    private List<Commande> commandesRecentes;
}
