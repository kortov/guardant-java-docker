package ru.kortov.guardant.licensing.exception;

/**
 * Исключения, возникающие при работе с библиотекой guardant
 */
public class GuardantProcessException extends RuntimeException {

    public GuardantProcessException(String message) {
        super(message);
    }

    public GuardantProcessException(Throwable cause) {
        super(cause);
    }

}
