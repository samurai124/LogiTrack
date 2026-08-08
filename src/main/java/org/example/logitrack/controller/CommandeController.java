package org.example.logitrack.controller;


import org.example.logitrack.dto.Commande_line_DTO;
import org.example.logitrack.model.Client;
import org.example.logitrack.model.Commande;
import org.example.logitrack.model.Produit;
import org.example.logitrack.service.ClientService;
import org.example.logitrack.service.CommandeService;
import org.example.logitrack.service.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public List<Commande>  getCommandes(){
        return commandeService.getAllCommandes();
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public ResponseEntity<Page<Commande>> getCommandesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCommande") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) String clientNom,
            @RequestParam(required = false) String status
    ){
        return ResponseEntity.ok(commandeService.searchCommandes(page, size, sortBy, sortDir, clientId, clientNom, status));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public Commande getCommandeById(@PathVariable long id){
        return commandeService.getCommandeById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public ResponseEntity<Void> addProduit(
            @RequestBody Commande_line_DTO commande_line_dto
            ){
        commandeService.addProductToCommande(commande_line_dto.getProduitId(), commande_line_dto.getOrderId(), commande_line_dto.getQuantite());
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public ResponseEntity<Void> updateCommandeStatus(
            @PathVariable long id,
            @RequestParam String status
    ){
        commandeService.editCommandeStatus(id,status);
        return ResponseEntity.ok().build();
    }


    @GetMapping("client/{clientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public List<Commande> getClientCommandes(
            @PathVariable long clientId
    ){
        return commandeService.getClientCommandes(clientId);
    }

}
