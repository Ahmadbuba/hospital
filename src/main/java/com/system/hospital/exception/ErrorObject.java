package com.system.hospital.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @AllArgsConstructor @NoArgsConstructor
public class ErrorObject {
    private HttpStatus status;
    private Object message;
    private LocalDateTime timestamp;

    public static ErrorObject instanceOf(HttpStatus status, String message) {
        return new ErrorObject(status, message, LocalDateTime.now());
    }

}
