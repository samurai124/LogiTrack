package org.example.logitrack.dto;

import lombok.*;
import org.example.logitrack.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
}
