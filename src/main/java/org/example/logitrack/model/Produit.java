package org.example.logitrack.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String categorie;
    private double prix;
    private int quantite;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    private List<LigneCommande> ligneCommandes;
}
