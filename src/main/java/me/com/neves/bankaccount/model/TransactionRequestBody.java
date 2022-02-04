package me.com.neves.bankaccount.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.com.neves.bankaccount.enumerator.TransactionType;
import me.com.neves.bankaccount.jpa.model.Transaction;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequestBody {
// ------------------------------ FIELDS ------------------------------

    @NotNull(message = "The account is required")
    @Schema(
            name = "account",
            type = "AccountCommand",
            title = "The account identification",
            example = "{\"agency\": \"1133-9\", \"number\": \"0087524-5\"}",
            required = true)
    private AccountCommand account;

    @NotNull(message = "The amount is required")
    @Schema(
            name = "amount",
            type = "Float",
            title = "The amount",
            example = "1.0",
            required = true)
    private BigDecimal amount;


    @NotNull(message = "The amount is required")
    @Schema(
            name = "origin",
            type = "String",
            title = "The origin",
            example = "Extra JU_ND II",
            required = true)
    private String origin;

    @NotNull(message = "The transaction type is required")
    @Schema(
            name = "transactionType",
            type = "TransactionType",
            title = "The transactionType",
            example = "DEBIT",
            required = true)
    private TransactionType transactionType;

// -------------------------- STATIC METHODS --------------------------

    public static Transaction mapFrom(TransactionRequestBody transactionRequestBody) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestBody.getAmount());
        transaction.setOrigin(transactionRequestBody.getOrigin());
        transaction.setType(transactionRequestBody.getTransactionType());
        transaction.setOwnerAccount(AccountCommand.mapFrom(transactionRequestBody.getAccount()));

        return transaction;
    }
}
