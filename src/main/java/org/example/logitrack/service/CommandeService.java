package org.example.logitrack.service;


import org.example.logitrack.model.Client;
import org.example.logitrack.model.Commande;
import org.example.logitrack.model.LigneCommande;
import org.example.logitrack.model.Produit;
import org.example.logitrack.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Commande>  getAllCommandes(){
        return commandeRepository.findAll();
    }

    @Transactional
    public void addCommande(Commande commande){
        commandeRepository.save(commande);
    }

    @Transactional
    public void editCommandeStatus(long id,String status){
        Commande commande = commandeRepository.findById(id).get();
        commande.setStatus(status);
        commandeRepository.save(commande);
    }

    @Transactional
    public Commande getCommandeById(long id){
        return commandeRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean addProductToCommande(long produit_id,long commande_id,int quantite){


        Produit produit = produitService.getProduitById(produit_id);
        Commande commande = commandeRepository.findById(commande_id).orElse(null);
        LigneCommande ligneCommande = new LigneCommande();

        if (produit == null || produit.getQuantite() < quantite || commande == null){
            return false;
        }

        produit.setQuantite(produit.getQuantite() - quantite);

        ligneCommande.setQuantite(quantite);
        ligneCommande.setProduit(produit);
        ligneCommande.setCommande(commande);

        commande.getLigneCommandes().add(ligneCommande);

        ligneCommandeService.addLigneCommande(ligneCommande);
        commandeRepository.save(commande);
        produitService.addProduit(produit);

        return true;
    }

    public List<Commande> getClientCommandes(long clientId){
        Client client = clientService.getClientById(clientId);
        return commandeRepository.findCommandeByClient(client);
    }
}
