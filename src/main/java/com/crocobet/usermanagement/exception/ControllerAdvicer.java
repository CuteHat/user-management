package com.crocobet.usermanagement.exception;

import com.crocobet.usermanagement.exception.model.GeneralErrorResponse;
import com.crocobet.usermanagement.exception.model.ValidationErrorResponse;
import com.crocobet.usermanagement.util.ExceptionUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerAdvicer {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ValidationErrorResponse handleInvalidRequestException(InvalidRequestException ex) {
        log.error("Invalid request exception encountered ", ex);
        return ValidationErrorResponse.from(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ValidationErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Constraint violation exception encountered ", ex);
        return ValidationErrorResponse.from(ex.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ValidationErrorResponse handleConstraintViolation(ConstraintViolationException ex) {
        log.error("Constraint violation exception encountered ", ex);
        return ValidationErrorResponse.from(ex.getConstraintViolations());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseBody
    public ValidationErrorResponse handleMissingHeader(MissingRequestHeaderException ex) {
        log.error("Missing header: {} in request ", ex.getHeaderName(), ex);
        return ValidationErrorResponse.from(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ValidationErrorResponse handleMissingRequestPart(MissingServletRequestParameterException ex) {
        log.error("Missing request part: {} in request", ex.getParameterName(), ex);
        return ValidationErrorResponse.from(ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public GeneralErrorResponse handleResourceNotFoundException(NotFoundException ex) {
        String message = ExceptionUtil.buildMessageFrom(ex);
        log.error(message);
        return new GeneralErrorResponse(message);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public GeneralErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
        log.error("Unauthorized exception caught:", ex);
        return new GeneralErrorResponse("Unauthorized");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    public GeneralErrorResponse handleForbiddenException(ForbiddenException ex) {
        log.error("Forbidden exception caught:", ex);
        return new GeneralErrorResponse("Forbidden");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralErrorResponse> defaultHandler(Exception exception) {
        log.error("General error caught:", exception);
        return new ResponseEntity<>(new GeneralErrorResponse("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
