package me.com.neves.bankaccount.utils;

import me.com.neves.bankaccount.enumerator.TransactionType;
import me.com.neves.bankaccount.jpa.model.AccountType;
import me.com.neves.bankaccount.jpa.model.Transaction;
import me.com.neves.bankaccount.model.*;

import java.math.BigDecimal;

public class ObjectCreatorUtil {
// -------------------------- STATIC METHODS --------------------------

    public static AccountRequestBody createAccountRequestBody() {
        AccountRequestBody accountRequestBody = new AccountRequestBody();
        accountRequestBody.setAgency(ConstantUtils.AG);
        accountRequestBody.setNumber(ConstantUtils.NUMBER);
        accountRequestBody.setAmount(new BigDecimal("11.5"));
        accountRequestBody.setOwnerIdentification(createDocumentCommand());
        accountRequestBody.setAccountType(createAccountTypeCommand());

        return accountRequestBody;
    }

    public static DocumentCommand createDocumentCommand() {
        DocumentCommand documentCommand = new DocumentCommand();
        documentCommand.setDocumentNumber("33169742884");
        documentCommand.setDocumentType("CPF");

        return documentCommand;
    }

    public static AccountTypeCommand createAccountTypeCommand() {
        AccountTypeCommand accountTypeCommand = new AccountTypeCommand();
        accountTypeCommand.setCode(ConstantUtils.ACCOUNT_TYPE_CODE);
        accountTypeCommand.setDescription("Salary account");

        return accountTypeCommand;
    }

    public static AccountCommand createAccountCommand(){
        AccountCommand accountCommand = new AccountCommand();
        accountCommand.setAgency(ConstantUtils.AG);
        accountCommand.setNumber(ConstantUtils.NUMBER);

        return accountCommand;
    }

    public static AccountType createAccountType(){
        AccountType accountType = new AccountType();
        accountType.setCode("SAL");
        accountType.setCode("Salary Account");

        return accountType;
    }

    public static AddressCommand createAddressCommand(){
        AddressCommand addressCommand = new AddressCommand();
        addressCommand.setCity("SP");
        addressCommand.setCountry("BR");
        addressCommand.setNumber("451b");
        addressCommand.setStreet("Street");

        return addressCommand;
    }


    public static ClientRequestBody createClientRequestBody(){
        ClientRequestBody clientRequestBody = new ClientRequestBody();
        clientRequestBody.setEmail("admin@admin.com");
        clientRequestBody.setName("Admin");
        clientRequestBody.setPhone("(11) 978457845");
        clientRequestBody.setAddress(createAddressCommand());
        clientRequestBody.setDocument(createDocumentCommand());

        return clientRequestBody;
    }


    public static TransactionRequestBody createTransactionRequestBody(){
        TransactionRequestBody transactionRequestBody = new TransactionRequestBody();
        transactionRequestBody.setAccount(createAccountCommand());
        transactionRequestBody.setAmount(new BigDecimal("11.5"));
        transactionRequestBody.setOrigin("tests");
        transactionRequestBody.setTransactionType(TransactionType.DEBIT);

        return transactionRequestBody;
    }
}
