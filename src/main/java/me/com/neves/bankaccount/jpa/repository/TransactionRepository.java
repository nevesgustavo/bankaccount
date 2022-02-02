package me.com.neves.bankaccount.jpa.repository;

import me.com.neves.bankaccount.jpa.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
// -------------------------- OTHER METHODS --------------------------

    List<Transaction> findByOwnerAccount_AgencyAndOwnerAccount_Number(String agency, String number);

    List<Transaction> findByOwnerAccountId(Integer ownerId);
}
