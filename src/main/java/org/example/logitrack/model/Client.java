package org.example.logitrack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private String nom;
    private String email ;
    private String telephone;
    private String ville;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Commande> commandes;
}
