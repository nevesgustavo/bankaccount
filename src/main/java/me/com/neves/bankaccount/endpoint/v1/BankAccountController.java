package me.com.neves.bankaccount.endpoint.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.jpa.model.Client;
import me.com.neves.bankaccount.model.AccountCommand;
import me.com.neves.bankaccount.model.AccountRequestBody;
import me.com.neves.bankaccount.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "Account", description = "Apis to manage account")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class BankAccountController {
// ------------------------------ FIELDS ------------------------------

    private final AccountService accountService;

// -------------------------- OTHER METHODS --------------------------

    @PostMapping
    @Operation(summary = "Create a new account")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Created account", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Client.class))})}
    )
    public ResponseEntity<Void> create(@RequestBody AccountRequestBody accountRequestBody) {
        accountService.create(AccountRequestBody.mapFrom(accountRequestBody));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/balance")
    @Operation(summary = "Return the balance")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Balance", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = BigDecimal.class))})}
    )
    public ResponseEntity<BigDecimal> getBalance(@RequestBody AccountCommand accountCommand) {
        Account account = accountService.findByAgencyAndNumber(accountCommand.getAgency(), accountCommand.getNumber());
        //TODO: if the logged user is not the account owner throws exception

        BigDecimal balance = BigDecimal.ZERO;
        if (account != null && account.getAmount() != null)
            balance = account.getAmount();

        return ResponseEntity.ok(balance);
    }
}
