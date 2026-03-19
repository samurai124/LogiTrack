package org.example.logitrack.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lignecommandes")
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantite;

    @ManyToOne
    @JsonIgnore
    private Commande commande;

    @ManyToOne
    @JsonIgnore
    private Produit produit;
}
