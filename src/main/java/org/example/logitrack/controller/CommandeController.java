package org.example.logitrack.controller;


import org.example.logitrack.model.Client;
import org.example.logitrack.model.Commande;
import org.example.logitrack.model.Produit;
import org.example.logitrack.service.ClientService;
import org.example.logitrack.service.CommandeService;
import org.example.logitrack.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Commande>  getCommandes(){
        return commandeService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public Commande getCommandeById(long id){
        return commandeService.getCommandeById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addCommande(
            @RequestParam long id_client
    ){
        Client client = clientService.getClientById(id_client);
        Commande commande = new Commande();
        commande.setStatus("EN_ATTENTE");
        commande.setDateCommande(LocalDateTime.now());
        commande.setClient(client);
        commandeService.addCommande(commande);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/{orderId}/product")
    public ResponseEntity<String> addProduit(
            @PathVariable long orderId,
            @RequestParam long  produitId,
            @RequestParam int quantite
    ){
        Produit produit = produitService.getProduitById(produitId);
        Commande commande = commandeService.getCommandeById(orderId);
        if (produit == null || commande == null || produit.getQuantite() < quantite){
            return ResponseEntity
                    .status(400)
                    .body("Produit not available or invalid request");
        }
        boolean success = commandeService.addProductToCommande(produitId, orderId, quantite);
        if (!success) {
            return ResponseEntity
                    .status(400)
                    .body("Failed to add product to order");
        }
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCommandeStatus(
            @PathVariable long id,
            @RequestParam String status
    ){
        commandeService.editCommandeStatus(id,status);
        return ResponseEntity.ok().build();
    }


    @GetMapping("client/{clientId}")
    public List<Commande> getClientCommandes(
            @PathVariable long clientId
    ){
        return commandeService.getClientCommandes(clientId);
    }

}
