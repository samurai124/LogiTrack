package org.example.logitrack.controller;


import org.example.logitrack.model.Client;
import org.example.logitrack.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping
    public List<Client> getAllClients(){
        return clientService.getAllClient();
    }

    @GetMapping("/{id}")
    public Client getClientById(@RequestParam long id){
        return clientService.getClinetById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addClient(
            @RequestParam String nom,
            @RequestParam String email,
            @RequestParam String telephone,
            @RequestParam String ville
    ){
        Client cLient = new Client();
        cLient.setNom(nom);
        cLient.setEmail(email);
        cLient.setTelephone(telephone);
        cLient.setVille(ville);
        clientService.addClient(cLient);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deletCLient(id);
        return ResponseEntity.ok().build();
    }



}
