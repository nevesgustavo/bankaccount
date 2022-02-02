package me.com.neves.bankaccount.jpa.repository;

import me.com.neves.bankaccount.jpa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
// -------------------------- OTHER METHODS --------------------------

    Account findByAgencyAndNumber(String agency, String number);
}
