package me.com.neves.bankaccount.exception;

public abstract class BaseRuntimeException extends RuntimeException {
// --------------------------- CONSTRUCTORS ---------------------------

    public BaseRuntimeException(String message) {
        super(message);
    }
}
