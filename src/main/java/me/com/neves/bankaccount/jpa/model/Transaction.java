package me.com.neves.bankaccount.jpa.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import me.com.neves.bankaccount.enumerator.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "transaction", indexes = {@Index(name = "unique_origin_amount_createdAt", columnList = "origin, amount, createdAt", unique = true)})
public class Transaction {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Account ownerAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "origin")
    private String origin;

    @Column(name = "type")
    private TransactionType type;

    @Column(name = "createdAt", nullable = false)
    private java.time.LocalDateTime createdAt;

// --------------------------- CONSTRUCTORS ---------------------------

    public Transaction() {
        this.createdAt = LocalDateTime.now();
    }

// --------------------- GETTER / SETTER METHODS ---------------------

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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Account getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(Account ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (!amount.equals(that.amount)) return false;
        return createdAt.equals(that.createdAt);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + amount.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + origin.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", origin=" + origin +
                ", createdAt=" + createdAt +
                '}';
    }
}
