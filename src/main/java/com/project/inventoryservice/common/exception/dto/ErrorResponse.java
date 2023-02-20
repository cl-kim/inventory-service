package com.project.inventoryservice.common.exception.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private int status;
    private String code;
    private List<FieldError> errors;

    private static final String DEFAULT_ERROR_MESSAGE = "ERROR";

    private ErrorResponse(final ErrorCode code, String customMessage) {
        this.timestamp = LocalDateTime.now();
        this.message = customMessage;
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(final ErrorCode code) {
        this.timestamp = LocalDateTime.now();
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }


    public static ErrorResponse of(final ErrorCode code, final String customMessage) {
        return new ErrorResponse(code, customMessage);
    }
    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

}
