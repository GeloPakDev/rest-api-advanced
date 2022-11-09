package com.epam.esm.exceptions;

import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoResultByFiltersException;
import com.epam.esm.exception.NoSuchEntityException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest request) {
        String message = "Requested resource not found (id=" + request.getParameter("id") + ")";
        int statusCode = HttpStatus.NOT_FOUND.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<Object> handleNoSuchEntityException(NoSuchEntityException e) {
        String message = "Requested resource not found (id=" + e.getMessage() + ")";
        int statusCode = HttpStatus.NOT_FOUND.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(NoResultByFiltersException.class)
    public ResponseEntity<Object> handleNoResultByFiltersException() {
        String message = "No results with applied filters";
        int statusCode = HttpStatus.NOT_FOUND.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String message = e.getLocalizedMessage();
        int statusCode = BAD_REQUEST.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(BAD_REQUEST)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleNullPointerException(InvalidParameterException e) {
        String message = e.getMessage();
        int statusCode = BAD_REQUEST.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(BAD_REQUEST)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public final ResponseEntity<Object> handleIncorrectParameterExceptions(IncorrectParameterException ex) {
        Iterator<Map.Entry<String, Object[]>> exceptions = ex.getExceptionResult().getExceptionMessages().entrySet().iterator();
        int statusCode = BAD_REQUEST.value();
        StringBuilder details = new StringBuilder();
        while (exceptions.hasNext()) {
            Map.Entry<String, Object[]> exception = exceptions.next();
            String message = exception.getKey();
            String detail = String.format(message, exception.getValue());
            details.append(detail).append(' ');
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(BAD_REQUEST)
                .statusCode(statusCode)
                .message(details.toString())
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
        String message = e.getLocalizedMessage();
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    public ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}