package me.com.neves.bankaccount.service.impl;

import me.com.neves.bankaccount.enumerator.TransactionType;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.service.TransactionAmountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreditServiceImpl implements TransactionAmountService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface TransactionAmountService ---------------------

    @Override
    public boolean supports(TransactionType transactionType) {
        return TransactionType.CREDIT.equals(transactionType);
    }

    @Override
    public void updateAccountAmount(Account account, BigDecimal amount) {
        account.setAmount(calculateCurrentAmount(account, amount));
    }

// -------------------------- OTHER METHODS --------------------------

    private BigDecimal calculateCurrentAmount(Account account, BigDecimal amount) {
        return account.getAmount().add(amount);
    }
}
