package com.about280.ashleigh.sports.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "position not found")
public class PositionNotFoundException extends RuntimeException {
}