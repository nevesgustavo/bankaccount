package me.com.neves.bankaccount.service;

import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.jpa.repository.AccountRepository;
import me.com.neves.bankaccount.model.AccountRequestBody;
import me.com.neves.bankaccount.model.ClientRequestBody;
import me.com.neves.bankaccount.utils.ConstantUtils;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {
// ------------------------------ FIELDS ------------------------------

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private AccountTypeService accountTypeService;

    @Mock
    private RedisLockService redisLockService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Unit] - create")
    void create() {
        Account account = accountService.create(AccountRequestBody.mapFrom(ObjectCreatorUtil.createAccountRequestBody()));
        Assertions.assertNotNull(account);
        Assertions.assertEquals(account.getAgency(), ConstantUtils.AG);
    }

    @Test
    void findByAgencyAndNumber() {
        Account account = accountService.findByAgencyAndNumber(ConstantUtils.AG, ConstantUtils.NUMBER);
        Assertions.assertNotNull(account);
        Assertions.assertEquals(account.getAgency(), ConstantUtils.AG);
    }

    @Test
    @DisplayName("[Unit] - save")
    void save() {
        Account account = accountService.save(AccountRequestBody.mapFrom(ObjectCreatorUtil.createAccountRequestBody()));
        Assertions.assertNotNull(account);
        Assertions.assertEquals(account.getAgency(), ConstantUtils.AG);
    }

    @BeforeEach
    void setUp() {
        BDDMockito.doNothing().when(redisLockService).lock(ArgumentMatchers.anyString());

        BDDMockito.when(clientService.findByDocumentNumberAndDocumentType(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(ClientRequestBody.mapFrom(ObjectCreatorUtil.createClientRequestBody()));

        BDDMockito.when(accountTypeService.findByCode(ArgumentMatchers.anyString()))
                .thenReturn(ObjectCreatorUtil.createAccountType());

        BDDMockito.when(accountRepository.save(ArgumentMatchers.any(Account.class)))
                .thenReturn(AccountRequestBody.mapFrom(ObjectCreatorUtil.createAccountRequestBody()));

        BDDMockito.when(accountRepository.findByAgencyAndNumber(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(AccountRequestBody.mapFrom(ObjectCreatorUtil.createAccountRequestBody()));
    }
}