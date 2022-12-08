package ru.kortov.guardant.licensing.exception;

public class GrdlicException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GrdlicException(String msg) {
        super(msg);
    }
}
