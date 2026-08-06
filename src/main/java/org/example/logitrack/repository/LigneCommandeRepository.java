package org.example.logitrack.repository;

import org.example.logitrack.model.LigneCommande;
import org.example.logitrack.model.Produit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {

    @Query("SELECT lc.produit FROM LigneCommande lc GROUP BY lc.produit ORDER BY SUM(lc.quantite) DESC")
    List<Produit> findMostOrderedProducts(Pageable pageable);
}
