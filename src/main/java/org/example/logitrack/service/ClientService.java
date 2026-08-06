package org.example.logitrack.service;

import jakarta.transaction.Transactional;
import org.example.logitrack.exception.ResourceNotFoundException;
import org.example.logitrack.model.Client;
import org.example.logitrack.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client addClient(Client client){
        return clientRepository.save(client);
    }

    @Transactional
    public List<Client> getAllClient(){
        return clientRepository.findAll();
    }

    @Transactional
    public Client getClientById(long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID : " + id));
    }

    @Transactional
    public void deleteClient(long id){
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : Client non trouvé avec l'ID : " + id);
        }
        clientRepository.deleteById(id);
    }


    @Transactional
    public Page<Client> paginatedClients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findAll(pageable);
    }

    @Transactional
    public Page<Client> chercherClientParNom(int page, int size, String sortDirection) {
        Sort sort = "DESC".equalsIgnoreCase(sortDirection)
                ? Sort.by("nom").descending()
                : Sort.by("nom").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return clientRepository.findAll(pageable);
    }

    @Transactional
    public Page<Client> searchClients(int page, int size, String sortBy, String sortDir, String nom) {
        String validSortBy = (sortBy != null && !sortBy.trim().isEmpty()) ? sortBy.trim() : "nom";
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, validSortBy));

        String cleanNom = (nom != null && !nom.trim().isEmpty()) ? nom.trim() : null;
        return clientRepository.searchClients(cleanNom, pageable);
    }
}



