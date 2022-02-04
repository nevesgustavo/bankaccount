package me.com.neves.bankaccount.service;

import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.exception.ClientAlreadyExistsException;
import me.com.neves.bankaccount.jpa.model.Client;
import me.com.neves.bankaccount.jpa.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
// ------------------------------ FIELDS ------------------------------

    private final ClientRepository clientRepository;

// -------------------------- OTHER METHODS --------------------------

    public Client create(Client client) {
        Client savedClient = clientRepository.findByDocumentNumberAndDocumentType(client.getDocumentNumber(), client.getDocumentType());
        if (savedClient != null)
            throw new ClientAlreadyExistsException(String.format("A client with document %s already exists", client.getDocumentNumber()));

        return clientRepository.save(client);
    }

    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public void delete(Client client){
        clientRepository.delete(client);
    }

    public Client findByDocumentNumberAndDocumentType(String documentNumber, String documentType) {
        return clientRepository.findByDocumentNumberAndDocumentType(documentNumber, documentType);
    }
}
