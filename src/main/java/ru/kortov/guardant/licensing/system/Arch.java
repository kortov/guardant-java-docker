package ru.kortov.guardant.licensing.system;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Варианты архитектуры ПК
 */
public enum Arch {
    X86_32("i386", "i686", "x86"),
    X86_64("amd64", "x86_64"),
    ARM8("arm");

    private final Set<String> patterns;

    Arch(final String... patterns) {
        this.patterns = new HashSet<>(Arrays.asList(patterns));
    }

    private boolean is(final String id) {
        return patterns.contains(id);
    }

    public static Arch getCurrent() {
        final String osArch = System.getProperty("os.arch");

        for (final Arch arch : Arch.values()) {
            if (arch.is(osArch)) {
                return arch;
            }
        }

        throw new UnsupportedOperationException(String.format("Architecture \"%s\" is not supported.", osArch));
    }
}
