package org.example.logitrack.service;


import jakarta.transaction.Transactional;
import org.example.logitrack.model.Client;
import org.example.logitrack.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client addClient(Client client){
      return  clientRepository.save(client);
    }

    @Transactional
    public List<Client> getAllClient(){
        return clientRepository.findAll();
    }

    @Transactional
    public  Client getClinetById(long id){
        return clientRepository.findById(id).get();
    }

    @Transactional
    public void deletCLient(long id){
        clientRepository.deleteById(id);
    }
}
