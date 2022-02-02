package me.com.neves.bankaccount.service;

import me.com.neves.bankaccount.enumerator.TransactionType;
import me.com.neves.bankaccount.jpa.model.Account;

import java.math.BigDecimal;

public interface TransactionAmountService {
// -------------------------- OTHER METHODS --------------------------

    boolean supports(TransactionType transactionType);

    void updateAccountAmount(Account account, BigDecimal amount);
}
