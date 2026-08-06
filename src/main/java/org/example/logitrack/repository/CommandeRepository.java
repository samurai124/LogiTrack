package org.example.logitrack.repository;

import org.example.logitrack.model.Client;
import org.example.logitrack.model.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findCommandeByClient(Client client);
    long countByStatus(String status);
    List<Commande> findTop5ByOrderByDateCommandeDesc();

    Page<Commande> findByClient(Client client, Pageable pageable);
    Page<Commande> findByStatusIgnoreCase(String status, Pageable pageable);

    @Query("SELECT c FROM Commande c WHERE " +
           "(:clientId IS NULL OR c.client.id = :clientId) AND " +
           "(:clientNom IS NULL OR :clientNom = '' OR LOWER(c.client.nom) LIKE LOWER(CONCAT('%', :clientNom, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR LOWER(c.status) = LOWER(:status))")
    Page<Commande> searchCommandes(
            @Param("clientId") Long clientId,
            @Param("clientNom") String clientNom,
            @Param("status") String status,
            Pageable pageable
    );
}
