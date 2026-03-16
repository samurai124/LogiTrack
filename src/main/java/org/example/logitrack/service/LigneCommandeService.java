package org.example.logitrack.service;

import org.example.logitrack.model.LigneCommande;
import org.example.logitrack.repository.LigneCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LigneCommandeService {

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;


    @Transactional
    public void addLigneCommande(LigneCommande ligneCommande){
        ligneCommandeRepository.save(ligneCommande);
    }

}
