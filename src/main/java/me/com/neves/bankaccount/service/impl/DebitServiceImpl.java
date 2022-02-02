package me.com.neves.bankaccount.service.impl;

import me.com.neves.bankaccount.enumerator.TransactionType;
import me.com.neves.bankaccount.exception.InsufficientFundsException;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.service.TransactionAmountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DebitServiceImpl implements TransactionAmountService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface TransactionAmountService ---------------------

    @Override
    public boolean supports(TransactionType transactionType) {
        return TransactionType.DEBIT.equals(transactionType);
    }

    @Override
    public void updateAccountAmount(Account account, BigDecimal amount) {
        if (!hasAmount(account, amount))
            throw new InsufficientFundsException(String.format("Insufficient funds for this transaction, available %s, debit: %s",
                    account.getAmount(), amount));

        account.setAmount(calculateCurrentAmount(account, amount));
    }

// -------------------------- OTHER METHODS --------------------------

    private BigDecimal calculateCurrentAmount(Account account, BigDecimal amount) {
        return account.getAmount().subtract(amount);
    }

    private boolean hasAmount(Account account, BigDecimal value) {
        BigDecimal amount = account.getAmount();
        return amount.compareTo(value) >= 0;
    }
}
