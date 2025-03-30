package com.gomezvaez.eventsourcing.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Alchemist not found")
public class AlchemistNotFoundException extends RuntimeException {
    public AlchemistNotFoundException(String id) {
        super("Alchemist not found: " + id);
    }
}