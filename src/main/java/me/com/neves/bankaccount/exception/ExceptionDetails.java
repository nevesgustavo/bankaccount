package me.com.neves.bankaccount.exception;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@Builder
public class ExceptionDetails {
// ------------------------------ FIELDS ------------------------------

    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private DateTime timestamp;
    private String path;
}
