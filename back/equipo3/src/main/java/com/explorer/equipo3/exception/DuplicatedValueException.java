package com.explorer.equipo3.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicatedValueException extends DataIntegrityViolationException {

    public DuplicatedValueException(String message) {
        super(message);
    }
}
