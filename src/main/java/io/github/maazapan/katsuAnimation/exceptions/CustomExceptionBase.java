package io.github.maazapan.katsuAnimation.exceptions;

public class CustomExceptionBase extends RuntimeException {

    public CustomExceptionBase(String message) {
        super(message);
    }

    public CustomExceptionBase(String message, Throwable cause) {
        super(message, cause);
    }
}
