package com.kinto.kintobackend.exception;

import com.kinto.kintobackend.config.ResponseError;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.security.InvalidParameterException;

@ControllerAdvice
@Log4j2
public class ExceptionControllerAdvice extends Throwable {
    @Autowired
    HttpServletRequest request;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseError> handlerConstraintViolationException(
            ConstraintViolationException ex,
            WebRequest request) {
        //log.error(ex.getMessage(), ex);
        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(),"Please fix error below",ex.getMessage(),((ServletWebRequest)request).getRequest().getRequestURI());
        return new ResponseEntity<ResponseError>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ResponseError> handlerInvalidParameterException(
            InvalidParameterException ex,
            WebRequest request){
        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(),
            "Required request parameter is not present",
            ex.getMessage(),
            ((ServletWebRequest)request).getRequest().getRequestURI());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseError> handlerNoHandlerFoundException(
            NoHandlerFoundException ex) {
        //log.error(ex.getMessage(), ex);
        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(),
                "Please fix error below",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<ResponseError>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsCollectionException.class)
    public ResponseEntity<ResponseError> handlerAlreadyExistsCollectionException(
            AlreadyExistsCollectionException ex) {
        //log.error(ex.getMessage(), ex);
        ResponseError error = new ResponseError(HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<ResponseError>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handlerResourceNotFoundException(
            ResourceNotFoundException ex) {
        //log.error(ex.getMessage(), ex);
        ResponseError error = new ResponseError(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<ResponseError>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseErrorException.class)
    public ResponseEntity<ResponseError> handlerResponseErrorException(
            ResponseErrorException ex) {
        //log.error(ex.getMessage(), ex);
        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<ResponseError>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> globalExceptionHandler(Exception ex) {
        ResponseError error = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<ResponseError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
