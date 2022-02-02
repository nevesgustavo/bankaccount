package me.com.neves.bankaccount.endpoint.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.model.AccountTypeCommand;
import me.com.neves.bankaccount.service.AccountTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "AccountType", description = "Apis to manage account type")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accountType")
public class BankAccountTypeController {
// ------------------------------ FIELDS ------------------------------

    private final AccountTypeService accountTypeService;

// -------------------------- OTHER METHODS --------------------------

    @GetMapping("/all")
    @Operation(summary = "Return all account types")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the types", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = AccountTypeCommand.class))})}
    )
    public ResponseEntity<List<AccountTypeCommand>> getAccountTypes() {
        return ResponseEntity.ok(AccountTypeCommand.mapFrom(accountTypeService.getAll()));
    }
}


