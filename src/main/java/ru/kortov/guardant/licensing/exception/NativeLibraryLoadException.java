package ru.kortov.guardant.licensing.exception;

/**
 * Ошибки загрузки нативных библиотек в {@link ClassLoader}
 */
public class NativeLibraryLoadException extends RuntimeException {

    public NativeLibraryLoadException(Throwable cause) {
        super(cause);
    }
}
