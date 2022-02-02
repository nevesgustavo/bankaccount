package me.com.neves.bankaccount.exception;

public class ClientAlreadyExistsException extends BaseRuntimeException {
// --------------------------- CONSTRUCTORS ---------------------------

    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
