package ru.kortov.guardant.licensing.exception;

/**
 * Ошибка отсутствия файла с нативной библиотекой
 */
public class NativeLibraryNotFoundException extends RuntimeException {

    public NativeLibraryNotFoundException(String libraryPath) {
        super("Native library not found on path %s".formatted(libraryPath));
    }
}
