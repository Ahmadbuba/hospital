package com.system.hospital.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private LocalDateTime timestamp;

}
