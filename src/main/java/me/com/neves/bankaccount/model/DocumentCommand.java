package me.com.neves.bankaccount.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Client;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentCommand {
// ------------------------------ FIELDS ------------------------------

    @NotBlank(message = "The documentNumber is required")
    @Schema(
            name = "documentNumber",
            type = "String",
            title = "The documentNumber",
            example = "33169742884",
            required = true)
    private String documentNumber;

    @NotBlank(message = "The documentType is required")
    @Schema(
            name = "documentType",
            type = "String",
            title = "The documentType",
            example = "CPF",
            required = true)
    private String documentType;

// -------------------------- STATIC METHODS --------------------------

    public static void mapFrom(DocumentCommand documentCommand, Client client) {
        client.setDocumentNumber(documentCommand.getDocumentNumber());
        client.setDocumentType(documentCommand.getDocumentType());
    }
}
