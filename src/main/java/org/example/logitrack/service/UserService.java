package org.example.logitrack.service;

import lombok.RequiredArgsConstructor;
import org.example.logitrack.dto.ChangePasswordDTO;
import org.example.logitrack.dto.UpdateUserDTO;
import org.example.logitrack.dto.UserResponseDTO;
import org.example.logitrack.enums.Role;
import org.example.logitrack.exception.ResourceNotFoundException;
import org.example.logitrack.model.User;
import org.example.logitrack.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ─── Read ────────────────────────────────────────────────────────────────

    /**
     * Returns all users, optionally filtered by role.
     */
    public List<UserResponseDTO> getAllUsers(Role role) {
        List<User> users = (role != null)
                ? userRepository.findByRole(role)
                : userRepository.findAll();

        return users.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    /**
     * Returns a single user by ID.
     */
    public UserResponseDTO getUserById(Long id) {
        User user = findOrThrow(id);
        return toResponseDTO(user);
    }

    // ─── Update ──────────────────────────────────────────────────────────────

    /**
     * Updates a user's nom, prenom, email and role.
     * Email uniqueness is validated against other users.
     */
    @Transactional
    public UserResponseDTO updateUser(Long id, UpdateUserDTO dto) {
        User user = findOrThrow(id);

        // Check email uniqueness only if the email actually changed
        if (!user.getEmail().equalsIgnoreCase(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException(
                    "Un utilisateur avec l'email '" + dto.getEmail() + "' existe déjà.");
        }

        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        return toResponseDTO(userRepository.save(user));
    }

    /**
     * Resets the password for a given user (admin use).
     */
    @Transactional
    public void changePassword(Long id, ChangePasswordDTO dto) {
        User user = findOrThrow(id);
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    // ─── Delete ──────────────────────────────────────────────────────────────

    /**
     * Deletes a user by ID.
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + id);
        }
        userRepository.deleteById(id);
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur non trouvé avec l'id : " + id));
    }

    private UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
