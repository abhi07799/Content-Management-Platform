package com.content.platform.exception;

import com.content.platform.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomGeneralExceptionHandler
{

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleResourceAlreadyExistException(ResourceAlreadyExistException ex, WebRequest request)
    {
        ErrorDto errorDto = ErrorDto.builder().status(HttpStatus.CONFLICT).timestamp(LocalDateTime.now()).message("Resource Already Exist").debugMessage(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request)
    {
        ErrorDto errorDto = ErrorDto.builder().status(HttpStatus.NOT_FOUND).timestamp(LocalDateTime.now()).message("Resource Not Found").debugMessage(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex, WebRequest request)
    {
        ErrorDto errorDto = ErrorDto.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).timestamp(LocalDateTime.now()).message("Something Went Wrong").debugMessage(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex, WebRequest request)
    {
        Map<String, Object> errors = new HashMap<>();
        List<ObjectError> errorObjects = ex.getBindingResult().getAllErrors();
        for (ObjectError errorObject : errorObjects)
        {
            FieldError fieldError = (FieldError) errorObject;
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorDto errorDto = ErrorDto.builder().status(HttpStatus.BAD_REQUEST).timestamp(LocalDateTime.now()).message("Validation Error").details(errors).build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
