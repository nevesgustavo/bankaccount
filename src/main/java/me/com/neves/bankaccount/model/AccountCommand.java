package me.com.neves.bankaccount.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Account;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCommand {
// ------------------------------ FIELDS ------------------------------

    @NotBlank(message = "The agency is required")
    @Schema(
            name = "agency",
            type = "String",
            title = "The agency",
            example = "1133-9",
            required = true)
    private String agency;

    @NotBlank(message = "The number is required")
    @Schema(
            name = "number",
            type = "String",
            title = "The number",
            example = "0087524-5",
            required = true)
    private String number;

// -------------------------- STATIC METHODS --------------------------

    public static Account mapFrom(AccountCommand accountCommand) {
        Account account = new Account();
        account.setAgency(accountCommand.getAgency());
        account.setNumber(accountCommand.getNumber());

        return account;
    }
}
