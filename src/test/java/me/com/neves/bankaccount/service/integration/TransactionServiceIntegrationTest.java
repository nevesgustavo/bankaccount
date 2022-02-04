package me.com.neves.bankaccount.service.integration;

import me.com.neves.bankaccount.BaseContextAwareTest;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.jpa.model.Transaction;
import me.com.neves.bankaccount.model.TransactionRequestBody;
import me.com.neves.bankaccount.service.AccountService;
import me.com.neves.bankaccount.service.TransactionService;
import me.com.neves.bankaccount.utils.ConstantUtils;
import me.com.neves.bankaccount.utils.ObjectCreatorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

class TransactionServiceIntegrationTest extends BaseContextAwareTest {
// ------------------------------ FIELDS ------------------------------

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    @DisplayName("[Integration] - create")
    void create() {
        Account account = accountService.findByAgencyAndNumber(ConstantUtils.AG, ConstantUtils.NUMBER);
        Assertions.assertNotNull(account.getAmount());
        BigDecimal balance = account.getAmount();

        Transaction transaction = TransactionRequestBody.mapFrom(ObjectCreatorUtil.createTransactionRequestBody());
        transaction.setAmount(new BigDecimal("1.0"));
        transactionService.create(transaction);

        account = accountService.findByAgencyAndNumber(ConstantUtils.AG, ConstantUtils.NUMBER);

        Assertions.assertEquals(balance.subtract(new BigDecimal("1.0")), account.getAmount());
    }

    @Test
    @DisplayName("[Integration] - findByOwnerAccountAgencyAndNumber")
    void findByOwnerAccountAgencyAndNumber() {
        Transaction transaction = TransactionRequestBody.mapFrom(ObjectCreatorUtil.createTransactionRequestBody());
        transaction.setAmount(new BigDecimal("2.0"));
        transactionService.create(transaction);

        List<Transaction> transactions = transactionService.findByOwnerAccountAgencyAndNumber(ConstantUtils.AG, ConstantUtils.NUMBER);
        Assertions.assertTrue(transactions.get(0).getAmount().compareTo(new BigDecimal("2.0")) == 0);
    }
}