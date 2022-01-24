package com.flock.springbootbackend.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.flock.springbootbackend.requestObjects.ResponseMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.naming.AuthenticationException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), exception.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> invalidDataExceptionHandling(DataIntegrityViolationException e, WebRequest req) {
            ErrorDetails errorResponse = new ErrorDetails(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUser.class)
    public ResponseEntity<?> invalidUserExceptionHandling(InvalidUser e, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> userNotFoundExceptionHandling(UserNotFound e, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidContact.class)
    public ResponseEntity<?> invalidContactExceptionHandling(InvalidContact e, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenError.class)
    public ResponseEntity<?> tokenGenerationExceptionHandling(TokenError e, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BinError.class)
    public ResponseEntity<?> BinErrorExceptionHandling(BinError e, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FileException.class)
    public ResponseEntity<?> fileExceptionHandling(FileException e, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> invalidUserExceptionHandling(AuthenticationException e, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), "Invalid Email or Password\n" + e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<?> invalidUserExceptionHandling(JWTCreationException e, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), "Could not create jwt token\n" + e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc, WebRequest req) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), "File too large!", req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
