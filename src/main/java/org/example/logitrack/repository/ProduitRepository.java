package org.example.logitrack.repository;

import org.example.logitrack.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Long> {


    List<Produit> findProduitByCategorie(String categorie);


    List<Produit> findProduitByPrixGreaterThan(double prixIsGreaterThan);
}
