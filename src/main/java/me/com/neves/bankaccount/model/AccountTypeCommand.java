package me.com.neves.bankaccount.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.com.neves.bankaccount.jpa.model.AccountType;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountTypeCommand {
// ------------------------------ FIELDS ------------------------------

    @NotBlank(message = "The code is required")
    @Schema(
            name = "code",
            type = "String",
            title = "The type code",
            example = "SAL",
            required = true)
    private String code;

    @NotBlank(message = "The description is required")
    @Schema(
            name = "description",
            type = "String",
            title = "The type description",
            example = "Salary",
            required = true)
    private String description;

// -------------------------- STATIC METHODS --------------------------

    public static AccountTypeCommand mapFrom(AccountType accountType) {
        AccountTypeCommand command = new AccountTypeCommand();
        command.setCode(accountType.getCode());
        command.setDescription(accountType.getDescription());
        return command;
    }

    public static List<AccountTypeCommand> mapFrom(List<AccountType> accountTypes) {
        List<AccountTypeCommand> accountTypeList = new ArrayList<>();
        for (AccountType accountType : accountTypes) {
            accountTypeList.add(mapFrom(accountType));
        }

        return accountTypeList;
    }

    public static AccountType mapFrom(AccountTypeCommand accountTypeCommand) {
        AccountType accountType = new AccountType();
        accountType.setCode(accountTypeCommand.getCode());
        accountType.setDescription(accountTypeCommand.getDescription());
        return accountType;
    }
}
