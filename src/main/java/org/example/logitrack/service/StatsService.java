package org.example.logitrack.service;

import lombok.RequiredArgsConstructor;
import org.example.logitrack.dto.DashboardStatsDTO;
import org.example.logitrack.model.Commande;
import org.example.logitrack.model.Produit;
import org.example.logitrack.repository.ClientRepository;
import org.example.logitrack.repository.CommandeRepository;
import org.example.logitrack.repository.LigneCommandeRepository;
import org.example.logitrack.repository.ProduitRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;

    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats() {
        long totalClients = clientRepository.count();
        long totalProduits = produitRepository.count();
        long totalCommandes = commandeRepository.count();

        long commandesEnAttente = commandeRepository.countByStatus("EN_ATTENTE");
        long commandesExpediees = commandeRepository.countByStatus("EXPEDIEE");
        long commandesLivrees = commandeRepository.countByStatus("LIVREE");

        List<Produit> produitsStockFaible = produitRepository.findByQuantiteLessThan(5);
        long stockFaibleCount = produitsStockFaible.size();

        List<Produit> mostOrderedList = ligneCommandeRepository.findMostOrderedProducts(PageRequest.of(0, 1));
        Produit produitLePlusCommande = mostOrderedList.isEmpty() ? null : mostOrderedList.get(0);

        List<Commande> commandesRecentes = commandeRepository.findTop5ByOrderByDateCommandeDesc();

        return DashboardStatsDTO.builder()
                .totalClients(totalClients)
                .totalProduits(totalProduits)
                .totalCommandes(totalCommandes)
                .commandesEnAttente(commandesEnAttente)
                .commandesExpediees(commandesExpediees)
                .commandesLivrees(commandesLivrees)
                .stockFaibleCount(stockFaibleCount)
                .produitsStockFaible(produitsStockFaible)
                .produitLePlusCommande(produitLePlusCommande)
                .commandesRecentes(commandesRecentes)
                .build();
    }

    @Transactional(readOnly = true)
    public List<Produit> getLowStockProducts(int threshold) {
        return produitRepository.findByQuantiteLessThan(threshold);
    }
}
