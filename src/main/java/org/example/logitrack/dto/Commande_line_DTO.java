package org.example.logitrack.dto;

import lombok.Data;

@Data
public class Commande_line_DTO {
    private  long produitId;
     private long orderId;
    private int quantite;
}
