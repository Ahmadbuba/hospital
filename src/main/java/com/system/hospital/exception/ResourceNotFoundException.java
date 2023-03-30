package com.system.hospital.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialversionUID = 1;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
