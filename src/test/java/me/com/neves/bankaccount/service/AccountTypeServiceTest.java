package me.com.neves.bankaccount.service;

import me.com.neves.bankaccount.jpa.model.AccountType;
import me.com.neves.bankaccount.jpa.repository.AccountTypeRepository;
import me.com.neves.bankaccount.model.AccountTypeCommand;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class AccountTypeServiceTest {
// ------------------------------ FIELDS ------------------------------

    @InjectMocks
    private AccountTypeService accountTypeService;

    @Mock
    private AccountTypeRepository accountTypeRepository;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Unit] - create type")
    void create() {
        AccountTypeCommand accountTypeCommand = ObjectCreatorUtil.createAccountTypeCommand();
        AccountType accountType = accountTypeService.create(AccountTypeCommand.mapFrom(accountTypeCommand));
        Assertions.assertNotNull(accountType);
        Assertions.assertEquals(accountType.getCode(), accountTypeCommand.getCode());
    }

    @Test
    @DisplayName("[Unit] - find type by code findByCode")
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

    @BeforeEach
    void setUp() {
        BDDMockito.when(accountTypeRepository.save(ArgumentMatchers.any(AccountType.class)))
                .thenReturn(AccountTypeCommand.mapFrom(ObjectCreatorUtil.createAccountTypeCommand()));

        BDDMockito.when(accountTypeRepository.findByCode(ArgumentMatchers.anyString()))
                .thenReturn(AccountTypeCommand.mapFrom(ObjectCreatorUtil.createAccountTypeCommand()));

        BDDMockito.when(accountTypeRepository.findAll())
                .thenReturn(Lists.newArrayList(AccountTypeCommand.mapFrom(ObjectCreatorUtil.createAccountTypeCommand())));
    }
}