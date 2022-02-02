package me.com.neves.bankaccount;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.com.neves.bankaccount.jpa.model.Account;
import me.com.neves.bankaccount.jpa.model.AccountType;
import me.com.neves.bankaccount.jpa.model.Client;
import me.com.neves.bankaccount.service.AccountService;
import me.com.neves.bankaccount.service.AccountTypeService;
import me.com.neves.bankaccount.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Bank account apis", version = "1.0", description = "Apis used for tests purpose, this make credit and debit"))
public class BankAccountApplication {
// ------------------------------ FIELDS ------------------------------

    private final AccountTypeService accountTypeService;
    private final ClientService clientService;
    private final AccountService accountService;

// -------------------------- OTHER METHODS --------------------------

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            createAccountTypes();
            log.info("Creating  default account types");

            createClient();
            log.info("Creating default client");

            createAccount();
            log.info("Creating default account");
        };
    }

    private void createAccountTypes() {
        AccountType accountType = new AccountType();
        accountType.setCode("SAL");
        accountType.setDescription("Salary Account");
        accountTypeService.create(accountType);

        accountType = new AccountType();
        accountType.setCode("CHK");
        accountType.setDescription("Checking Account");
        accountTypeService.create(accountType);
    }

    private void createClient() {
        Client client = new Client();
        client.setDocumentNumber("33169742884");
        client.setDocumentType("CPF");
        client.setPhone("(11) 97585-8574");
        client.setName("John Sky");
        client.setEmail("john.sky@neves.com");
        client.setStreet("St 41");
        client.setNumber("78b");
        client.setCity("Orland");
        client.setCountry("EUA");

        clientService.create(client);
    }

    private void createAccount() {
        Account account = new Account();
        account.setAgency("1133-9");
        account.setNumber("0087524-5");
        account.setAmount(new BigDecimal("11.5"));
        account.setType(accountTypeService.findByCode("SAL"));
        account.setOwner(clientService.findByDocumentNumberAndDocumentType("33169742884", "CPF"));
        accountService.create(account);
    }

// --------------------------- main() method ---------------------------

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }
}
