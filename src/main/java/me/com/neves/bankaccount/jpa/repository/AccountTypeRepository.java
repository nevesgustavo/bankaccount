package me.com.neves.bankaccount.jpa.repository;

import me.com.neves.bankaccount.jpa.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
// -------------------------- OTHER METHODS --------------------------

    AccountType findByCode(String code);
}
