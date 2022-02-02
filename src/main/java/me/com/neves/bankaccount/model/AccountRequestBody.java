package me.com.neves.bankaccount.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.jpa.model.Client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestBody {
// ------------------------------ FIELDS ------------------------------

    @NotBlank(message = "The agency code is required")
    @Schema(
            name = "agency",
            type = "String",
            title = "The agency code",
            example = "1133-9",
            required = true)
    private String agency;

    @NotBlank(message = "The account number is required")
    @Schema(
            name = "number",
            type = "String",
            title = "The account number",
            example = "0087524-5",
            required = true)
    private String number;

    @Schema(
            name = "amount",
            type = "Float",
            title = "The total amount money",
            example = "11.5")
    private BigDecimal amount;

    @NotNull(message = "The account type is required")
    @Schema(
            name = "accountType",
            type = "AccountTypeCommand",
            title = "The account type",
            example = "{\"code\": \"CPF\", \"description\": \"33169742884\"}",
            required = true)
    private AccountTypeCommand accountType;

    @NotNull(message = "The owner document is required")
    @Schema(
            name = "ownerIdentification",
            type = "DocumentCommand",
            title = "The owner identification",
            example = "{\"documentType\": \"CPF\", \"documentNumber\": \"33169742884\"}",
            required = true)
    private DocumentCommand ownerIdentification;

// -------------------------- STATIC METHODS --------------------------

    public static Account mapFrom(AccountRequestBody accountRequestBody) {
        Account account = new Account();
        account.setAgency(accountRequestBody.getAgency());
        account.setNumber(accountRequestBody.getNumber());
        account.setAmount(accountRequestBody.getAmount());
        account.setType(AccountTypeCommand.mapFrom(accountRequestBody.getAccountType()));

        Client client = new Client();
        client.setDocumentType(accountRequestBody.getOwnerIdentification().getDocumentType());
        client.setDocumentNumber(accountRequestBody.getOwnerIdentification().getDocumentNumber());

        account.setOwner(client);
        return account;
    }
}
