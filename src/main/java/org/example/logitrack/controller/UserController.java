package org.example.logitrack.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.logitrack.dto.ChangePasswordDTO;
import org.example.logitrack.dto.RegisterDTO;
import org.example.logitrack.dto.UpdateUserDTO;
import org.example.logitrack.dto.UserResponseDTO;
import org.example.logitrack.enums.Role;
import org.example.logitrack.service.AuthService;
import org.example.logitrack.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;
    private final AuthService authService;


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false) Role role
    ) {
        return ResponseEntity.ok(userService.getAllUsers(role));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.getAllUsers(null)
                        .stream()
                        .filter(u -> u.getEmail().equalsIgnoreCase(registerDTO.getEmail()))
                        .findFirst()
                        .orElseThrow());
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDTO dto
    ) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }


    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordDTO dto
    ) {
        userService.changePassword(id, dto);
        return ResponseEntity.noContent().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
