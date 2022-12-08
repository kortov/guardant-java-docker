package ru.kortov.guardant.licensing.exception;

import ru.kortov.guardant.licensing.system.Arch;
import ru.kortov.guardant.licensing.system.OS;

/**
 * Ошибка отсутствия поддержки текущей платформы
 */
public class UnsupportedPlatformException extends RuntimeException {

    public UnsupportedPlatformException(final OS os, final Arch arch) {
        super(String.format("Operating system \"%s\" and architecture \"%s\" are not supported.", os, arch));
    }
}
