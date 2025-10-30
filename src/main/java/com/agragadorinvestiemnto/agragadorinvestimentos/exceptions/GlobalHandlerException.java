package com.agragadorinvestiemnto.agragadorinvestimentos.exceptions;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto emailNotFoundException(EmailNotFoundException ex){
        ErrorDto errorDto = new ErrorDto(ex.getMessage(),
                "Please, verify your email information",
                LocalDateTime.now());
        return errorDto;
    }

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto idNotFoundException(IdNotFoundException ex){
        ErrorDto errorDto = new ErrorDto(ex.getMessage(),
                "Please, verify your id information",
                LocalDateTime.now());
        return errorDto;
    }


}
