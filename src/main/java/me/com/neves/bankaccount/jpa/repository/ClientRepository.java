package me.com.neves.bankaccount.jpa.repository;

import me.com.neves.bankaccount.jpa.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
// -------------------------- OTHER METHODS --------------------------

    Client findByDocumentNumberAndDocumentType(String documentNumber, String documentType);
}
