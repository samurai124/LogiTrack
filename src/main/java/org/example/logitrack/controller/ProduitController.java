package org.example.logitrack.controller;
import org.example.logitrack.model.Produit;
import org.example.logitrack.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produit")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping
    public List<Produit> getAllClients(){
        return produitService.getAllProduits();
    }

    @GetMapping("/{id}")
    public Produit getProfuitById(@RequestParam long id){
        return produitService.getProduitById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addProduit(
            @RequestParam String nom,
            @RequestParam String categorie,
            @RequestParam double prix,
            @RequestParam int quantite
    ){
        Produit produit = new Produit();
        produit.setNom(nom);
        produit.setCategorie(categorie);
        produit.setPrix(prix);
        produit.setQuantite(quantite);

        produitService.addProduit(produit);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@RequestParam long id){
        produitService.deleteProduit(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("category/{category}")
    public List<Produit> produitsParCategorie(
            @PathVariable String category
    ){
        return produitService.produitsParCategorie(category);
    }

    @GetMapping("price/{price}")
    public List<Produit> produitsByPrix(
            @PathVariable double prix
    ){
        return produitService.produitsGraterthan(prix);
    }

}
