package me.com.neves.bankaccount.endpoint.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Client;
import me.com.neves.bankaccount.model.ClientRequestBody;
import me.com.neves.bankaccount.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Client", description = "Apis to manage client")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {
// ------------------------------ FIELDS ------------------------------

    private final ClientService clientService;

// -------------------------- OTHER METHODS --------------------------

    @PostMapping
    @Operation(summary = "Create a new client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Created client", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Client.class))})}
    )
    public ResponseEntity<Void> create(@RequestBody ClientRequestBody clientRequestBody) {
        clientService.create(ClientRequestBody.mapFrom(clientRequestBody));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Find all clients", tags = "Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Client.class))))})
    public ResponseEntity<Page<Client>> getAll(Pageable pageable) {
        return ResponseEntity.ok(clientService.findAll(pageable));
    }
}
