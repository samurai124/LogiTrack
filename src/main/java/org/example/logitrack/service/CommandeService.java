package org.example.logitrack.service;

import org.example.logitrack.exception.BadRequestException;
import org.example.logitrack.exception.ResourceNotFoundException;
import org.example.logitrack.model.Client;
import org.example.logitrack.model.Commande;
import org.example.logitrack.model.LigneCommande;
import org.example.logitrack.model.Produit;
import org.example.logitrack.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private LigneCommandeService ligneCommandeService;

    @Autowired
    private ClientService clientService;

    @Transactional
    public List<Commande> getAllCommandes(){
        return commandeRepository.findAll();
    }

    @Transactional
    public void addCommande(Commande commande){
        commandeRepository.save(commande);
    }

    @Transactional
    public void editCommandeStatus(long id, String status){
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec l'ID : " + id));
        commande.setStatus(status);
        commandeRepository.save(commande);
    }

    @Transactional
    public Commande getCommandeById(long id){
        return commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec l'ID : " + id));
    }

    @Transactional
    public boolean addProductToCommande(long produit_id, long commande_id, int quantite){
        Produit produit = produitService.getProduitById(produit_id);
        Commande commande = getCommandeById(commande_id);

        if (quantite <= 0) {
            throw new BadRequestException("La quantité demandée doit être supérieure à zéro.");
        }

        if (produit.getQuantite() < quantite) {
            throw new BadRequestException("Quantité en stock insuffisante pour le produit '" + produit.getNom()
                    + "'. Stock disponible : " + produit.getQuantite() + ", demandé : " + quantite);
        }

        produit.setQuantite(produit.getQuantite() - quantite);

        LigneCommande ligneCommande = new LigneCommande();
        ligneCommande.setQuantite(quantite);
        ligneCommande.setProduit(produit);
        ligneCommande.setCommande(commande);

        commande.getLigneCommandes().add(ligneCommande);

        ligneCommandeService.addLigneCommande(ligneCommande);
        commandeRepository.save(commande);
        produitService.addProduit(produit);

        return true;
    }

    @Transactional(readOnly = true)
    public List<Commande> getClientCommandes(long clientId){
        Client client = clientService.getClientById(clientId);
        return commandeRepository.findCommandeByClient(client);
    }

    @Transactional(readOnly = true)
    public Page<Commande> searchCommandes(int page, int size, String sortBy, String sortDir,
                                         Long clientId, String clientNom, String status) {
        String validSortBy = (sortBy != null && !sortBy.trim().isEmpty()) ? sortBy.trim() : "dateCommande";
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, validSortBy));

        String cleanNom = (clientNom != null && !clientNom.trim().isEmpty()) ? clientNom.trim() : null;
        String cleanStatus = (status != null && !status.trim().isEmpty() && !"ALL".equalsIgnoreCase(status)) ? status.trim() : null;

        return commandeRepository.searchCommandes(clientId, cleanNom, cleanStatus, pageable);
    }
}

