package me.com.neves.bankaccount.handler;

import lombok.extern.slf4j.Slf4j;
import me.com.neves.bankaccount.exception.*;
import org.joda.time.DateTime;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {
// -------------------------- OTHER METHODS --------------------------

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, HttpServletRequest request) {
        log.error("Runtime Exception: {}", ex.getMessage());
        return new ResponseEntity<>(prepareDetails(ex, "Runtime Exception", HttpStatus.INTERNAL_SERVER_ERROR.value(), request),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionDetails prepareDetails(Exception ex, String title, int status, HttpServletRequest request) {
        return prepareDetails(ex, title, status, ex.getMessage(), request);
    }

    @ExceptionHandler(value = {ClientAlreadyExistsException.class})
    public ResponseEntity<Object> handleAlreadyExistsException(Exception ex, HttpServletRequest request) {
        log.error("Already exists exception: {}", ex.getMessage());
        return new ResponseEntity<>(prepareDetails(ex, "Already exists exception", HttpStatus.CONFLICT.value(), request), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ClientNotFoundException.class, TypeNotFoundException.class, AccountNotFoundException.class})
    public ResponseEntity<Object> handleClientNotFoundException(Exception ex, HttpServletRequest request) {
        log.error("Not found exception: {}", ex.getMessage());
        return new ResponseEntity<>(prepareDetails(ex, "Not found exception", HttpStatus.BAD_REQUEST.value(), request), HttpStatus.BAD_REQUEST);
    }

    private ExceptionDetails prepareDetails(Exception ex, String title, int status, String message, HttpServletRequest request) {
        return ExceptionDetails.builder()
                .timestamp(DateTime.now())
                .status(status)
                .title(title)
                .details(message)
                .developerMessage(ex.getClass().getSimpleName())
                .path(request.getRequestURL().toString())
                .build();
    }
}
