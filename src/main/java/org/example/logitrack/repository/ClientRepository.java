package org.example.logitrack.repository;

import org.example.logitrack.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Page<Client> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    @Query("SELECT c FROM Client c WHERE " +
           "(:nom IS NULL OR :nom = '' OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :nom, '%')))")
    Page<Client> searchClients(@Param("nom") String nom, Pageable pageable);


    @Query("SELECT c FROM Client c WHERE LOWER(c.nom) = LOWER(:nom)")
    List<Client> findClientByNom(@Param("nom") String nom);

    boolean existsByNom(String nom);
}

