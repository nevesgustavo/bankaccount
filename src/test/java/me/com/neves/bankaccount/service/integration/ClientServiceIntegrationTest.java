package me.com.neves.bankaccount.service.integration;

import me.com.neves.bankaccount.BaseContextAwareTest;
import me.com.neves.bankaccount.exception.ClientAlreadyExistsException;
import me.com.neves.bankaccount.jpa.model.Client;
import me.com.neves.bankaccount.model.ClientRequestBody;
import me.com.neves.bankaccount.model.DocumentCommand;
import me.com.neves.bankaccount.service.ClientService;
import me.com.neves.bankaccount.utils.ObjectCreatorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class ClientServiceIntegrationTest extends BaseContextAwareTest {
// ------------------------------ FIELDS ------------------------------

    private static final String DOCUMENT_NUMBER = "47854541121";
    private static final String DOCUMENT_TYPE = "CPF";
    @Autowired
    private ClientService clientService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Integration] - create_when_success")
    void create_when_success() {
        Client client = clientService.findByDocumentNumberAndDocumentType(DOCUMENT_NUMBER, DOCUMENT_TYPE);
        if (client != null)
            clientService.delete(client);

        ClientRequestBody clientRequestBody = ObjectCreatorUtil.createClientRequestBody();
        Client newClient = ClientRequestBody.mapFrom(clientRequestBody);
        newClient.setDocumentNumber(DOCUMENT_NUMBER);
        newClient.setDocumentType(DOCUMENT_TYPE);

        client = clientService.create(newClient);
        Assertions.assertNotNull(client);
        Assertions.assertEquals(client.getDocumentNumber(), DOCUMENT_NUMBER);
    }

    @Test
    @DisplayName("[Integration] - create_when_throws")
    void create_when_throws() {
        ClientRequestBody clientRequestBody = ObjectCreatorUtil.createClientRequestBody();

        Client client = clientService.findByDocumentNumberAndDocumentType(clientRequestBody.getDocument().getDocumentNumber(),
                clientRequestBody.getDocument().getDocumentType());
        if (client == null)
            clientService.create(client);

        Assertions.assertThrows(ClientAlreadyExistsException.class, () ->
                clientService.create(ClientRequestBody.mapFrom(clientRequestBody))
        );
    }

    @Test
    @DisplayName("[Integration] - findAll clients")
    void findAll() {
        Page<Client> clients = clientService.findAll(PageRequest.of(1, 10));
        Assertions.assertNotNull(clients);
    }

    @Test
    @DisplayName("[Integration] - findByDocumentNumberAndDocumentType")
    void findByDocumentNumberAndDocumentType() {
        Client client = clientService.findByDocumentNumberAndDocumentType("33169742884", "CPF");
        Assertions.assertNotNull(client);
        Assertions.assertEquals(client.getDocumentNumber(), "33169742884");
    }
}