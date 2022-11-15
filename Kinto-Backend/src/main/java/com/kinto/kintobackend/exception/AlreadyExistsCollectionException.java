package com.kinto.kintobackend.exception;

public class AlreadyExistsCollectionException extends RuntimeException{
    public AlreadyExistsCollectionException(String message){
        super(message);
    }
}
