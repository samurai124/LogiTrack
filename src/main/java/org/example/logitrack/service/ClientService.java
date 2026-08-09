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
    public Page<Client> SortParNom(int page, int size, String sortDirection) {
        if (sortDirection.isBlank()){
            Pageable pageable = PageRequest.of(page, size);
            return clientRepository.findAll(pageable);
        }
        Sort sort = "DESC".equalsIgnoreCase(sortDirection)
                ? Sort.by("nom").descending()
                : Sort.by("nom").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return clientRepository.findAll(pageable);
    }

    public List<Client> findByNom(String nom) {
        List<Client> clients = clientRepository.findClientByNom(nom);

        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("Client non trouvé avec le nom : " + nom);
        }

        return clients;
    }


}



