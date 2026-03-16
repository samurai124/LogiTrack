package org.example.logitrack.service;

import org.example.logitrack.repository.LigneCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LigneCommandeService {

    @Autowired
    private LigneCommandeRepository commandeRepository;

}
