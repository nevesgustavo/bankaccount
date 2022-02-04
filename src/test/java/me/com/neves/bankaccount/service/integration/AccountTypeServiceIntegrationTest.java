package me.com.neves.bankaccount.service.integration;

import me.com.neves.bankaccount.BaseContextAwareTest;
import me.com.neves.bankaccount.jpa.model.AccountType;
import me.com.neves.bankaccount.model.AccountTypeCommand;
import me.com.neves.bankaccount.service.AccountTypeService;
import me.com.neves.bankaccount.utils.ConstantUtils;
import me.com.neves.bankaccount.utils.ObjectCreatorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


class AccountTypeServiceIntegrationTest extends BaseContextAwareTest {
// ------------------------------ FIELDS ------------------------------

    @Autowired
    private AccountTypeService accountTypeService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Integration] - create type")
    void create() {
        AccountTypeCommand accountTypeCommand = new AccountTypeCommand();
        accountTypeCommand.setCode("Tests");
        accountTypeCommand.setDescription("Salary account");

        AccountType accountType = accountTypeService.create(AccountTypeCommand.mapFrom(accountTypeCommand));
        Assertions.assertNotNull(accountType);
        Assertions.assertEquals(accountType.getCode(), accountTypeCommand.getCode());
    }

    @Test
    @DisplayName("[Integration] - find type by code findByCode")
    void findByCode() {
        AccountType accountType = accountTypeService.findByCode(ConstantUtils.ACCOUNT_TYPE_CODE);
        Assertions.assertNotNull(accountType);
        Assertions.assertEquals(accountType.getCode(), ConstantUtils.ACCOUNT_TYPE_CODE);
    }

    @Test
    void getAll() {
        List<AccountType> types = accountTypeService.getAll();
        Assertions.assertNotNull(types);
        Assertions.assertTrue(types.stream().anyMatch(d -> d.getCode().equals(ConstantUtils.ACCOUNT_TYPE_CODE)));
    }
}