package me.com.neves.bankaccount.exception;

public class AccountNotFoundException extends BaseRuntimeException {
// --------------------------- CONSTRUCTORS ---------------------------

    public AccountNotFoundException(String message) {
        super(message);
    }
}
