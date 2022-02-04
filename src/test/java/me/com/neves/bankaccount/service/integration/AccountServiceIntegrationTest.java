package me.com.neves.bankaccount.service.integration;

import me.com.neves.bankaccount.BaseContextAwareTest;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.model.AccountRequestBody;
import me.com.neves.bankaccount.service.AccountService;
import me.com.neves.bankaccount.utils.ConstantUtils;
import me.com.neves.bankaccount.utils.ObjectCreatorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class AccountServiceIntegrationTest extends BaseContextAwareTest {
// ------------------------------ FIELDS ------------------------------

    @Autowired
    private AccountService accountService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Integration] - create")
    void create() {
        Account newAccount = AccountRequestBody.mapFrom(ObjectCreatorUtil.createAccountRequestBody());
        newAccount.setAgency("457845");
        newAccount.setNumber("7845784578");
        Account account = accountService.create(newAccount);
        Assertions.assertNotNull(account);
    }

    @Test
    void findByAgencyAndNumber() {
        Account account = accountService.findByAgencyAndNumber(ConstantUtils.AG, ConstantUtils.NUMBER);
        Assertions.assertNotNull(account);
        Assertions.assertEquals(account.getAgency(), ConstantUtils.AG);
    }

    @Test
    @DisplayName("[Integration] - save")
    void save() {
        Account account = accountService.findByAgencyAndNumber(ConstantUtils.AG, ConstantUtils.NUMBER);
        Assertions.assertNotNull(account);
        Assertions.assertEquals(account.getAgency(), ConstantUtils.AG);
    }
}