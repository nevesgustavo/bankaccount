package me.com.neves.bankaccount.service;

import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.exception.ClientNotFoundException;
import me.com.neves.bankaccount.exception.TypeNotFoundException;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.jpa.model.AccountType;
import me.com.neves.bankaccount.jpa.model.Client;
import me.com.neves.bankaccount.jpa.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
// ------------------------------ FIELDS ------------------------------

    private final AccountRepository accountRepository;
    private final ClientService clientService;
    private final AccountTypeService accountTypeService;
    private final RedisLockService redisLockService;

    private static final String LOCK_KEY = "lock_account";

// -------------------------- OTHER METHODS --------------------------

    public Account create(Account account) {
        redisLockService.lock(LOCK_KEY);
        try {
            Client client = clientService.findByDocumentNumberAndDocumentType(account.getOwner().getDocumentNumber(), account.getOwner().getDocumentType());
            if (client == null)
                throw new ClientNotFoundException(String.format("The client with document %s of type %s doesn't exists",
                        account.getOwner().getDocumentNumber(), account.getOwner().getDocumentType()));

            AccountType accountType = accountTypeService.findByCode(account.getType().getCode());

            if (accountType == null)
                throw new TypeNotFoundException(String.format("The account type %s was not found", account.getType().getCode()));

            account.setOwner(client);
            account.setType(accountType);

            return save(account);
        }finally {
            redisLockService.unlock(LOCK_KEY);
        }
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findByAgencyAndNumber(String agency, String number) {
        return accountRepository.findByAgencyAndNumber(agency, number);
    }
}
