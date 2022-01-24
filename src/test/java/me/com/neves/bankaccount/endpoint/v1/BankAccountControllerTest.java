package me.com.neves.bankaccount.endpoint.v1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankAccountControllerTest {
// ------------------------------ FIELDS ------------------------------

    @Autowired
    private BankAccountController bankAccountController;

// -------------------------- OTHER METHODS --------------------------

    @Test
    void hello() {
        Assertions.assertNotNull(bankAccountController.hello());
    }
}