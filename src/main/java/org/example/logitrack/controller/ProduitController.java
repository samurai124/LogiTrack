package org.example.logitrack.controller;
import org.example.logitrack.model.Produit;
import org.example.logitrack.service.ProduitService;
import org.springframework.data.domain.Page;
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
    public List<Produit> getAllProduits(){
        return produitService.getAllProduits();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Produit>> getProduitsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nom") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) Double minPrix,
            @RequestParam(required = false) Double maxPrix,
            @RequestParam(required = false) Boolean stockFaible,
            @RequestParam(defaultValue = "10") Integer seuilStock
    ){
        return ResponseEntity.ok(produitService.searchProduits(page, size, sortBy, sortDir, nom, categorie, minPrix, maxPrix, stockFaible, seuilStock));
    }

    @GetMapping("/stock-faible")
    public ResponseEntity<Page<Produit>> getStockFaiblePaginated(
            @RequestParam(defaultValue = "10") int seuil,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "quantite") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ){
        return ResponseEntity.ok(produitService.getProduitsStockFaible(seuil, page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public Produit getProduitById(@PathVariable long id){
        return produitService.getProduitById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addProduit(
           @RequestBody Produit produit_request
    ){
        Produit produit = new Produit();
        produit.setNom(produit_request.getNom());
        produit.setCategorie(produit_request.getCategorie());
        produit.setPrix(produit_request.getPrix());
        produit.setQuantite(produit_request.getQuantite());

        produitService.addProduit(produit);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable long id){
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
