package me.com.neves.bankaccount.endpoint.v1;

import me.com.neves.bankaccount.model.AccountTypeCommand;
import me.com.neves.bankaccount.service.AccountTypeService;
import me.com.neves.bankaccount.utils.ObjectCreatorUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class BankAccountTypeControllerTest {
// ------------------------------ FIELDS ------------------------------

    @InjectMocks
    private BankAccountTypeController bankAccountTypeController;

    @Mock
    private AccountTypeService accountTypeService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Unit] - getAccountTypes")
    void getAccountTypes() {
        ResponseEntity<List<AccountTypeCommand>> responseEntity = bankAccountTypeController.getAccountTypes();
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertFalse(responseEntity.getBody().isEmpty());
    }

    @BeforeEach
    void setUp() {
        BDDMockito.when(accountTypeService.getAll())
                .thenReturn(Lists.newArrayList(ObjectCreatorUtil.createAccountType()));
    }
}