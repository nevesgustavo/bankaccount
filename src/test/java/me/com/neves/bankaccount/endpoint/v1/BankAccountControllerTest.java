package me.com.neves.bankaccount.endpoint.v1;

import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.model.AccountRequestBody;
import me.com.neves.bankaccount.service.AccountService;
import me.com.neves.bankaccount.utils.ObjectCreatorUtil;
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

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
class BankAccountControllerTest {
// ------------------------------ FIELDS ------------------------------

    @InjectMocks
    private BankAccountController bankAccountController;

    @Mock
    private AccountService accountService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Unit] - Create bank account")
    void create() {
        ResponseEntity<Void> responseEntity = bankAccountController.create(ObjectCreatorUtil.createAccountRequestBody());
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("[Unit] - Get Balance")
    void getBalance() {
        ResponseEntity<BigDecimal> balance = bankAccountController.getBalance(ObjectCreatorUtil.createAccountCommand());
        Assertions.assertEquals(balance.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(balance.getBody(), new BigDecimal("11.5"));
    }

    @BeforeEach
    void setUp() {
        BDDMockito.when(accountService.create(ArgumentMatchers.any(Account.class)))
                .thenReturn(AccountRequestBody.mapFrom(ObjectCreatorUtil.createAccountRequestBody()));

        BDDMockito.when(accountService.findByAgencyAndNumber(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(AccountRequestBody.mapFrom(ObjectCreatorUtil.createAccountRequestBody()));
    }
}