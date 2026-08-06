package org.example.logitrack.service;

import jakarta.transaction.Transactional;
import org.example.logitrack.exception.ResourceNotFoundException;
import org.example.logitrack.model.Produit;
import org.example.logitrack.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Transactional
    public List<Produit> getAllProduits(){
        return produitRepository.findAll();
    }

    @Transactional
    public Produit getProduitById(long id){
        return produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'ID : " + id));
    }

    @Transactional
    public void addProduit(Produit produit){
        produitRepository.save(produit);
    }

    @Transactional
    public void deleteProduit(long id){
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : Produit non trouvé avec l'ID : " + id);
        }
        produitRepository.deleteById(id);
    }

    @Transactional
    public List<Produit> produitsParCategorie(String categorie){
        return produitRepository.findProduitByCategorie(categorie);
    }

    @Transactional
    public List<Produit> produitsGraterthan(double prix){
        return produitRepository.findProduitByPrixGreaterThan(prix);
    }

    @Transactional
    public Page<Produit> searchProduits(int page, int size, String sortBy, String sortDir,
                                        String nom, String categorie, Double minPrix, Double maxPrix,
                                        Boolean stockFaible, Integer seuilStock) {
        String validSortBy = (sortBy != null && !sortBy.trim().isEmpty()) ? sortBy.trim() : "nom";
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, validSortBy));

        String cleanNom = (nom != null && !nom.trim().isEmpty()) ? nom.trim() : null;
        String cleanCat = (categorie != null && !categorie.trim().isEmpty()) ? categorie.trim() : null;
        int seuil = (seuilStock != null) ? seuilStock : 10;

        return produitRepository.searchProduits(cleanNom, cleanCat, minPrix, maxPrix, stockFaible, seuil, pageable);
    }

    @Transactional
    public Page<Produit> getProduitsStockFaible(int threshold, int page, int size, String sortBy, String sortDir) {
        String validSortBy = (sortBy != null && !sortBy.trim().isEmpty()) ? sortBy.trim() : "quantite";
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, validSortBy));

        return produitRepository.findByQuantiteLessThanEqual(threshold, pageable);
    }
}

