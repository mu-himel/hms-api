package technology.grameen.gk.health.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import technology.grameen.gk.health.api.responses.ExceptionResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        String message =  (errors.size()>0)? errors.get(errors.size()-1):"Sorry! Invalid Input";
        ExceptionResponse apiError =
                new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY, message , errors);
        return handleExceptionInternal(
                ex, apiError, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ExceptionResponse apiError =
                new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY, errors.toString().replaceAll("\\[|]",""), errors);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ExceptionResponse apiError =
                new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), error);
        return new ResponseEntity<Object>(
                apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            CustomException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        errors.add(ex.getMessage());

        ExceptionResponse apiError =
                new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
        return new ResponseEntity<>(
                apiError,  HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
