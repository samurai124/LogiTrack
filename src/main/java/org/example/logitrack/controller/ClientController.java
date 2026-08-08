package org.example.logitrack.controller;

import org.example.logitrack.model.Client;
import org.example.logitrack.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public List<Client> getAllClients(){
        return clientService.getAllClient();
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public ResponseEntity<Page<Client>> getClientsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nom") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String nom
    ){
        return ResponseEntity.ok(clientService.searchClients(page, size, sortBy, sortDir, nom));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGENT')")
    public Client getClientById(@PathVariable long id){
        return clientService.getClientById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> addClient(
           @RequestBody Client client_request
    ){
        Client cLient = new Client();
        cLient.setNom(client_request.getNom());
        cLient.setEmail(client_request.getEmail());
        cLient.setTelephone(client_request.getTelephone());
        cLient.setVille(client_request.getVille());
        clientService.addClient(cLient);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}
