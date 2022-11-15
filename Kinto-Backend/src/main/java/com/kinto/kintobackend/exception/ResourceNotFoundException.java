package com.kinto.kintobackend.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
            super(message);
        }
}
