package com.department.transportation.trip.statistics.handler;

import com.department.transportation.trip.statistics.core.exceptions.BadRequestException;
import com.department.transportation.trip.statistics.core.exceptions.ExceptionListResponse;
import com.department.transportation.trip.statistics.core.exceptions.ExceptionResponse;
import com.department.transportation.trip.statistics.core.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 21:49
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequestException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleUnprocessableEntity(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> details = new ArrayList<>(List.of());
        ex.getConstraintViolations()
                .forEach(c -> details.add(String.format("%s %s", c.getPropertyPath().toString(), get(c.getMessage()))));
        return ResponseEntity.badRequest().body(new ExceptionListResponse(details.stream().sorted().toList()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getObjectResponseEntity(ex);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getObjectResponseEntity(ex);
    }

    private ResponseEntity<Object> getObjectResponseEntity(BindException ex) {
        List<String> details = new ArrayList<>(List.of());

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(String.format("%s %s", error.getField(), (error.isBindingFailure() ? "Invalid type" : get(error.getDefaultMessage()))));
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            details.add(String.format("%s %s", error.getObjectName(), error.getDefaultMessage()));
        }

        return ResponseEntity.badRequest().body(new ExceptionListResponse(details.stream().sorted().toList()));
    }
}
