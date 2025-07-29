package com.example.footballmanagerapp.dto.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ExceptionResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static ExceptionResponse of(String message, Map<String, String> errors) {
        return new ExceptionResponse(message, errors, LocalDateTime.now());
    }
}