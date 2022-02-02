package me.com.neves.bankaccount.exception;

public class ClientNotFoundException extends BaseRuntimeException {
// --------------------------- CONSTRUCTORS ---------------------------

    public ClientNotFoundException(String message) {
        super(message);
    }
}
