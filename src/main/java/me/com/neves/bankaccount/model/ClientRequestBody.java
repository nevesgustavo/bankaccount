package me.com.neves.bankaccount.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Client;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequestBody {
// ------------------------------ FIELDS ------------------------------

    @NotBlank(message = "The name is required")
    @Schema(
            name = "name",
            type = "String",
            title = "The name",
            example = "John Sky",
            required = true)
    private String name;

    @NotBlank(message = "The email is required")
    @Email(message = "The e-mail is not valid")
    @Schema(
            name = "email",
            type = "String",
            title = "The email",
            example = "john.sky@neves.com",
            required = true)
    private String email;

    @Schema(
            name = "phone",
            type = "String",
            title = "The phone",
            example = "(11) 97585-8574")
    private String phone;

    @NotNull(message = "The document is required")
    @Schema(
            name = "document",
            type = "DocumentCommand",
            title = "The client document identification",
            example = "{\"documentType\": \"CPF\", \"documentNumber\": \"33169742884\"}",
            required = true)
    private DocumentCommand document;

    @NotNull(message = "The owner document is required")
    @Schema(
            name = "address",
            type = "AddressCommand",
            title = "The address",
            example = "{\"street\": \"St 41\", \"number\": \"78b\", \"city\": \"Orland\", \"country\": \"EUA\"}",
            required = true)
    private AddressCommand address;


    public static Client mapFrom(ClientRequestBody clientRequestBody){
        Client client = new Client();
        AddressCommand.mapFrom(clientRequestBody.getAddress(), client);
        DocumentCommand.mapFrom(clientRequestBody.getDocument(), client);
        client.setName(clientRequestBody.getName());
        client.setEmail(clientRequestBody.getEmail());
        client.setPhone(clientRequestBody.getPhone());
        return client;
    }
}
