package org.example.logitrack.service;


import jakarta.transaction.Transactional;
import org.example.logitrack.model.Client;
import org.example.logitrack.model.Produit;
import org.example.logitrack.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return produitRepository.findById(id).get();
    }

    @Transactional
    public void addProduit(Produit produit){
        produitRepository.save(produit);
    }

    @Transactional
    public void deleteProduit(long id){
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



}
