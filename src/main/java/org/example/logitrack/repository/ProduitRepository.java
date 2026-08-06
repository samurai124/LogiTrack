package org.example.logitrack.repository;

import org.example.logitrack.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findProduitByCategorie(String categorie);
    List<Produit> findProduitByPrixGreaterThan(double prixIsGreaterThan);
    List<Produit> findByQuantiteLessThan(int threshold);

    Page<Produit> findByCategorieContainingIgnoreCase(String categorie, Pageable pageable);
    Page<Produit> findByPrixBetween(double minPrix, double maxPrix, Pageable pageable);
    Page<Produit> findByQuantiteLessThanEqual(int maxQuantite, Pageable pageable);

    @Query("SELECT p FROM Produit p WHERE " +
           "(:nom IS NULL OR :nom = '' OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:categorie IS NULL OR :categorie = '' OR LOWER(p.categorie) LIKE LOWER(CONCAT('%', :categorie, '%'))) AND " +
           "(:minPrix IS NULL OR p.prix >= :minPrix) AND " +
           "(:maxPrix IS NULL OR p.prix <= :maxPrix) AND " +
           "(:stockFaible IS NULL OR :stockFaible = false OR p.quantite <= :seuilStock)")
    Page<Produit> searchProduits(
            @Param("nom") String nom,
            @Param("categorie") String categorie,
            @Param("minPrix") Double minPrix,
            @Param("maxPrix") Double maxPrix,
            @Param("stockFaible") Boolean stockFaible,
            @Param("seuilStock") Integer seuilStock,
            Pageable pageable
    );
}
