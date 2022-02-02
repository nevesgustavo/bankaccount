package me.com.neves.bankaccount.jpa.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "account", indexes = {@Index(name = "unique_agency_number", columnList = "agency, number", unique = true)})
public class Account {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "agency", nullable = false)
    private String agency;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(optional = false)
    @JsonBackReference
    private Client owner;

    @ManyToOne(optional = false)
    private AccountType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Transaction> transactions = new HashSet<>();

    @Column(name = "createdAt", nullable = false)
    private java.time.LocalDateTime createdAt;

// --------------------------- CONSTRUCTORS ---------------------------

    public Account() {
        this.createdAt = LocalDateTime.now();
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (id != account.id) return false;
        if (agency != null ? !agency.equals(account.agency) : account.agency != null) return false;
        return number != null ? number.equals(account.number) : account.number == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (agency != null ? agency.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", agency='" + agency + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
