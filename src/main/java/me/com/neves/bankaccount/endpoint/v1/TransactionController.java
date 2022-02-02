package me.com.neves.bankaccount.endpoint.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Transaction;
import me.com.neves.bankaccount.model.TransactionRequestBody;
import me.com.neves.bankaccount.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transaction", description = "Apis to save transactions")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
// ------------------------------ FIELDS ------------------------------

    private final TransactionService transactionService;

// -------------------------- OTHER METHODS --------------------------

    @PostMapping
    @Operation(summary = "Create a transaction")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Created transaction", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Transaction.class))})}
    )
    public ResponseEntity<Void> create(@RequestBody TransactionRequestBody transactionRequestBody) {
        transactionService.create(TransactionRequestBody.mapFrom(transactionRequestBody));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{agency}/{number}")
    @Operation(summary = "Find transactions by agency and number", tags = "Transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Transaction.class))))})
    public ResponseEntity<List<Transaction>> getByAccountAgencyAndNumber(@PathVariable String agency, @PathVariable String number) {
        return ResponseEntity.ok(transactionService.findByOwnerAccountAgencyAndNumber(agency, number));
    }
}
