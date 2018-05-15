package com.locallibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Object already existent")  // 404
public class ExistingObjectException extends RuntimeException {

}
