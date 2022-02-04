package me.com.neves.bankaccount.service;

import me.com.neves.bankaccount.exception.ClientAlreadyExistsException;
import me.com.neves.bankaccount.jpa.model.Client;
import me.com.neves.bankaccount.jpa.repository.ClientRepository;
import me.com.neves.bankaccount.model.ClientRequestBody;
import me.com.neves.bankaccount.utils.ObjectCreatorUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class ClientServiceTest {
// ------------------------------ FIELDS ------------------------------

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Unit] - create_when_success")
    void create_when_success() {
        BDDMockito.when(clientRepository.findByDocumentNumberAndDocumentType(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(null);

        ClientRequestBody clientRequestBody = ObjectCreatorUtil.createClientRequestBody();
        Client client = clientService.create(ClientRequestBody.mapFrom(clientRequestBody));
        Assertions.assertNotNull(client);
        Assertions.assertEquals(client.getDocumentNumber(), clientRequestBody.getDocument().getDocumentNumber());
    }

    @Test
    @DisplayName("[Unit] - create_when_throws")
    void create_when_throws() {
        ClientRequestBody clientRequestBody = ObjectCreatorUtil.createClientRequestBody();

        Assertions.assertThrows(ClientAlreadyExistsException.class, () ->
                clientService.create(ClientRequestBody.mapFrom(clientRequestBody))
        );
    }

    @Test
    @DisplayName("[Unit] - findAll clients")
    void findAll() {
        Page<Client> clients = clientService.findAll(PageRequest.of(1, 10));
        Assertions.assertNotNull(clients);
    }

    @Test
    @DisplayName("[Unit] - findByDocumentNumberAndDocumentType")
    void findByDocumentNumberAndDocumentType() {
        Client client = clientService.findByDocumentNumberAndDocumentType("33169742884", "CPF");
        Assertions.assertNotNull(client);
        Assertions.assertEquals(client.getDocumentNumber(), "33169742884");
    }

    @BeforeEach
    void setUp() {
        List<Client> clients = Lists.newArrayList(ClientRequestBody.mapFrom(ObjectCreatorUtil.createClientRequestBody()));

        BDDMockito.when(clientRepository.findByDocumentNumberAndDocumentType(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(ClientRequestBody.mapFrom(ObjectCreatorUtil.createClientRequestBody()));

        BDDMockito.when(clientRepository.save(ArgumentMatchers.any(Client.class)))
                .thenReturn(ClientRequestBody.mapFrom(ObjectCreatorUtil.createClientRequestBody()));

        BDDMockito.when(clientRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(clients, Pageable.unpaged(), clients.size()));
    }
}