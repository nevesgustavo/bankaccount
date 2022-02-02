package me.com.neves.bankaccount.service;

import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.enumerator.TransactionType;
import me.com.neves.bankaccount.exception.AccountNotFoundException;
import me.com.neves.bankaccount.exception.TransactionAmountImplNotFoundException;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.jpa.model.Transaction;
import me.com.neves.bankaccount.jpa.repository.TransactionRepository;
import me.com.neves.bankaccount.model.TransactionMessageQueue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
// ------------------------------ FIELDS ------------------------------

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final List<TransactionAmountService> transactionAmountServices;
    private final QueueService queueService;

// -------------------------- OTHER METHODS --------------------------

    public Transaction create(Transaction transaction) {
        Account account = accountService.findByAgencyAndNumber(transaction.getOwnerAccount().getAgency(), transaction.getOwnerAccount().getNumber());
        if (account == null)
            throw new AccountNotFoundException(String.format("The account was not found, agency: %s, number: %s",
                    transaction.getOwnerAccount().getAgency(), transaction.getOwnerAccount().getNumber()));

        TransactionAmountService tranctionAmountImpl = findTransactionAmountImpl(transaction.getType());
        if (tranctionAmountImpl == null) {
            throw new TransactionAmountImplNotFoundException(String.format("There is no implementation for %s",
                    transaction.getType().name()));
        }

        tranctionAmountImpl.updateAccountAmount(account, transaction.getAmount());
        transaction.setOwnerAccount(account);

        Transaction resultTransaction = transactionRepository.save(transaction);

        queueService.sendToConsumer(TransactionMessageQueue.mapFrom(transaction));

        return resultTransaction;
    }

    private TransactionAmountService findTransactionAmountImpl(TransactionType transactionType) {
        for (TransactionAmountService service : transactionAmountServices) {
            if (service.supports(transactionType))
                return service;
        }

        return null;
    }

    public List<Transaction> findByOwnerAccountAgencyAndNumber(String agency, String number) {
        return transactionRepository.findByOwnerAccount_AgencyAndOwnerAccount_Number(agency, number);
    }
}
