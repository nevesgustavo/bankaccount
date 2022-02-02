package me.com.neves.bankaccount.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.com.neves.bankaccount.jpa.model.Client;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressCommand {
// ------------------------------ FIELDS ------------------------------

    private String street;
    private String number;
    private String city;
    private String country;

    public static void mapFrom(AddressCommand addressCommand, Client client){
        client.setCity(addressCommand.getCity());
        client.setCountry(addressCommand.getCountry());
        client.setNumber(addressCommand.getNumber());
        client.setStreet(addressCommand.getStreet());
    }
}
