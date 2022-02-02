package me.com.neves.bankaccount.service;

import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.jpa.model.AccountType;
import me.com.neves.bankaccount.jpa.repository.AccountTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeService {
// ------------------------------ FIELDS ------------------------------

    private final AccountTypeRepository accountTypeRepository;

// -------------------------- OTHER METHODS --------------------------

    public AccountType create(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    public AccountType findByCode(String code) {
        return accountTypeRepository.findByCode(code);
    }

    public List<AccountType> getAll() {
        return accountTypeRepository.findAll();
    }
}
