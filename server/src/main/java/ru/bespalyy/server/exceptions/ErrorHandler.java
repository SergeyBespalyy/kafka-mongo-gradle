package ru.bespalyy.server.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Класс описывающий ErrorHandler для централизованной обработки ошибок.
 */

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ValidationIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleValidationIdException(final ValidationIdException e) {
        log.warn("Исключение ValidationIdException {}", e.getMessage());
        return new ErrorResponse("ValidationIdException", e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleSqlException(final DataIntegrityViolationException e) {
        log.warn("Ошибка DataIntegrityViolationException {}", e.getMessage());
        return new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowableException(final Throwable e) {
        log.warn("Ошибка Throwable {}", e.getMessage());
        return new ErrorResponse("ValidationException", e.getMessage());
    }

}


