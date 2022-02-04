package me.com.neves.bankaccount.endpoint.v1;

import me.com.neves.bankaccount.jpa.model.Transaction;
import me.com.neves.bankaccount.model.TransactionRequestBody;
import me.com.neves.bankaccount.service.TransactionService;
import me.com.neves.bankaccount.utils.ConstantUtils;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class TransactionControllerTest {
// ------------------------------ FIELDS ------------------------------

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Unit] - create transaction")
    void create() {
        ResponseEntity<Void> responseEntity = transactionController.create(ObjectCreatorUtil.createTransactionRequestBody());
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("[Unit] - getByAccountAgencyAndNumber")
    void getByAccountAgencyAndNumber() {
        ResponseEntity<List<Transaction>> responseEntity = transactionController.getByAccountAgencyAndNumber(ConstantUtils.AG, ConstantUtils.NUMBER);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(responseEntity.getBody().get(0).getOwnerAccount().getAgency(), ConstantUtils.AG);
    }

    @BeforeEach
    void setUp() {
        BDDMockito.when(transactionService.create(ArgumentMatchers.any(Transaction.class)))
                .thenReturn(TransactionRequestBody.mapFrom(ObjectCreatorUtil.createTransactionRequestBody()));

        BDDMockito.when(transactionService.findByOwnerAccountAgencyAndNumber(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(Lists.newArrayList(TransactionRequestBody.mapFrom(ObjectCreatorUtil.createTransactionRequestBody())));
    }
}