package me.com.neves.bankaccount.endpoint.v1;

import me.com.neves.bankaccount.jpa.model.Client;
import me.com.neves.bankaccount.model.ClientRequestBody;
import me.com.neves.bankaccount.service.ClientService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class ClientControllerTest {
// ------------------------------ FIELDS ------------------------------

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Unit] create client")
    void create() {
        ClientRequestBody clientRequestBody = ObjectCreatorUtil.createClientRequestBody();
        ResponseEntity<Void> responseEntity = clientController.create(clientRequestBody);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("[Unit] get all clients")
    void getAll() {
        ClientRequestBody clientRequestBody = ObjectCreatorUtil.createClientRequestBody();
        ResponseEntity<Page<Client>> responseEntity = clientController.getAll(PageRequest.of(1, 10));
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(responseEntity.getBody().getTotalElements(), 1);
        Assertions.assertEquals(responseEntity.getBody().get().findFirst().orElse(null).getName(), clientRequestBody.getName());
    }

    @BeforeEach
    void setUp() {
        List<Client> clients = Lists.newArrayList(ClientRequestBody.mapFrom(ObjectCreatorUtil.createClientRequestBody()));

        BDDMockito.when(clientService.create(ArgumentMatchers.any(Client.class)))
                .thenReturn(ClientRequestBody.mapFrom(ObjectCreatorUtil.createClientRequestBody()));

        BDDMockito.when(clientService.findAll(ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(clients, Pageable.unpaged(), clients.size()));
    }
}