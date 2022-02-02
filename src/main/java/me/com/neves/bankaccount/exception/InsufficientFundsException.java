package me.com.neves.bankaccount.exception;

public class InsufficientFundsException extends BaseRuntimeException {
// --------------------------- CONSTRUCTORS ---------------------------

    public InsufficientFundsException(String message) {
        super(message);
    }
}
