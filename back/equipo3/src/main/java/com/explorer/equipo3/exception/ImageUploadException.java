package com.explorer.equipo3.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class ImageUploadException extends RuntimeException{

    public ImageUploadException(String message) {
        super(message);
    }
}
