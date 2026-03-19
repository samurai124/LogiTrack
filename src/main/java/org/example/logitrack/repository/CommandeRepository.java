package org.example.logitrack.repository;

import org.example.logitrack.model.Client;
import org.example.logitrack.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande,Long> {


    List<Commande> findCommandeByClient(Client client);


}
