package ru.kortov.guardant.licensing.system;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public enum OS {
    OSX("^[Mm]ac OS X$"),
    LINUX("^[Ll]inux$"),
    WINDOWS("^[Ww]indows.*");

    private final Set<Pattern> patterns;

    OS(final String... patterns) {
        this.patterns = new HashSet<>();

        for (final String pattern : patterns) {
            this.patterns.add(Pattern.compile(pattern));
        }
    }

    private boolean is(final String id) {
        for (final Pattern pattern : patterns) {
            if (pattern.matcher(id).matches()) {
                return true;
            }
        }
        return false;
    }

    public static OS getCurrent() {
        final String osName = System.getProperty("os.name");

        for (final OS os : OS.values()) {
            if (os.is(osName)) {
                return os;
            }
        }

        throw new UnsupportedOperationException(String.format("Operating system \"%s\" is not supported.", osName));
    }
}
