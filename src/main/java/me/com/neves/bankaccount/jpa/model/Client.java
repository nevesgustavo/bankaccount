package me.com.neves.bankaccount.jpa.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "client", indexes = {@Index(name = "unique_documentNumber_documentType", columnList = "documentNumber, documentType", unique = true)})
public class Client {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Account> accounts = new HashSet<>();

    @Column(name = "documentNumber", nullable = false)
    private String documentNumber;

    @Column(name = "documentType", nullable = false)
    private String documentType;

    @Column(name = "createdAt", nullable = false)
    private java.time.LocalDateTime createdAt;

// --------------------------- CONSTRUCTORS ---------------------------

    public Client() {
        this.createdAt = LocalDateTime.now();
    }
}
