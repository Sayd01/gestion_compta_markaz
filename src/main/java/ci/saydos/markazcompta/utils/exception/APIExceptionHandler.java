/*
 * Created on 2023-08-28 ( Time 13:26:33 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2023 O-sey. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.exception;

import ci.saydos.markazcompta.utils.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * APIExceptionHandler
 *
 * @author Sayd & Souleymane
 */

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(EntityNotFoundException exception, WebRequest webRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(exception.getStatus())
                .hasError(true)
                .httpCode(notFound.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiErrorResponse, notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidEntityException exception, WebRequest webRequest) {
        final HttpStatus badRequest= HttpStatus.BAD_REQUEST;
        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(exception.getStatus())
                .hasError(true)
                .httpCode(badRequest.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiErrorResponse, badRequest);
    }

    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<ApiErrorResponse> handleException(FunctionalException exception, WebRequest webRequest) {
        final HttpStatus badRequest= HttpStatus.BAD_REQUEST;
        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(exception.getStatus())
                .hasError(true)
                .httpCode(badRequest.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiErrorResponse, badRequest);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InternalErrorException exception, WebRequest webRequest) {
        final HttpStatus internalServerError= HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(exception.getStatus())
                .hasError(true)
                .httpCode(internalServerError.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiErrorResponse, internalServerError);
    }


    Status status = Status.builder()
            .code("901")
            .message("Les informations de connexion fournies (mot de passe et/ou adresse e-mail) sont incorrectes")
            .build();

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleException() {
        final HttpStatus badRequest= HttpStatus.BAD_REQUEST;
        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(status)
                .hasError(true)
                .httpCode(badRequest.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiErrorResponse, badRequest);
    }




}
