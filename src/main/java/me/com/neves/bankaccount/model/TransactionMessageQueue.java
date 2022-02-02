package me.com.neves.bankaccount.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionMessageQueue {
// ------------------------------ FIELDS ------------------------------

    private Long id;
    private BigDecimal amount;
    private String origin;
    private String type;
    private LocalDateTime createdAt;
    private String agency;
    private String number;
    private BigDecimal balance;


    public static TransactionMessageQueue mapFrom(Transaction transaction){
        return TransactionMessageQueue
                .builder()
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreatedAt())
                .id(transaction.getId())
                .origin(transaction.getOrigin())
                .type(transaction.getType().name())
                .agency(transaction.getOwnerAccount().getAgency())
                .number(transaction.getOwnerAccount().getNumber())
                .balance(transaction.getOwnerAccount().getAmount())
                .build();
    }
}
