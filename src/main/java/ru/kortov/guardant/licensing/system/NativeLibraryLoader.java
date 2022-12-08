package ru.kortov.guardant.licensing.system;

import grdlic.GrdInt;
import grdlic.api;
import ru.kortov.guardant.licensing.exception.NativeLibraryLoadException;
import ru.kortov.guardant.licensing.exception.NativeLibraryNotFoundException;
import ru.kortov.guardant.licensing.exception.UnsupportedPlatformException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Класс для загрузки нативных библиотек
 */
public class NativeLibraryLoader {
    public static final String TEMP_DIR_PREFIX = "kortov_guardant-tmp";

    private static volatile NativeLibraryLoader instance;

    private NativeLibraryLoader() {
        load();
        printApiVersion();
    }

    public static void loadLib() {
        if (instance == null) {
            synchronized (NativeLibraryLoader.class) {
                if (instance == null) {
                    instance = new NativeLibraryLoader();
                }
            }
        }
    }


    /**
     * Функция копирует нативную библиотеку текущей платформы в TEMP директорию и загружает в текущий ClassLoader
     *
     * @throws UnsupportedPlatformException если текущая платформа не поддерживается
     * @throws NativeLibraryLoadException   при ошибке загрузки нативной библиотеки
     */
    private void load() {
        final OS os = OS.getCurrent();
        final Arch arch = Arch.getCurrent();

        final String libraryPath = getPlatformSpecifiedLibraryPath(os, arch);

        try {
            final Path tempDirectory = Files.createTempDirectory(TEMP_DIR_PREFIX);
            final InputStream binary = NativeLibraryLoader.class.getResourceAsStream(libraryPath);
            if (binary == null) {
                throw new NativeLibraryNotFoundException(libraryPath);
            }
            final Path destination = tempDirectory.resolve("./" + libraryPath).normalize();
            Files.createDirectories(destination.getParent());
            Files.copy(binary, destination);
            binary.close();
            System.load(destination.toString());
        } catch (IOException e) {
            throw new NativeLibraryLoadException(e);
        }
    }

    @SuppressWarnings("java:S1075")
    private String getPlatformSpecifiedLibraryPath(OS os, Arch arch) {
        String path;
        switch (os) {
            case WINDOWS -> {
                if (arch == Arch.X86_32) {
                    path = "/native/lib/windows/x86_32/grdlic_java.dll";
                } else if (arch == Arch.X86_64) {
                    path = "/native/lib/windows/x86_64/grdlic_java.dll";
                } else {
                    throw new UnsupportedPlatformException(os, arch);
                }
            }
            case LINUX -> {
                if (arch == Arch.X86_32) {
                    path = "/native/lib/linux/x86_32/liblibgrdlic_java.so";
                } else if (arch == Arch.X86_64) {
                    path = "/native/lib/linux/x86_64/liblibgrdlic_java.so";
                } else {
                    throw new UnsupportedPlatformException(os, arch);
                }
            }
            case OSX -> {
                if (arch == Arch.X86_64) {
                    path = "/native/lib/macos/x86_64/libgrdlic_java.dylib";
                } else {
                    throw new UnsupportedPlatformException(os, arch);
                }
            }
            default -> throw new UnsupportedPlatformException(os, arch);
        }
        return path;
    }

    private void printApiVersion() {
        GrdInt majorVersion = new GrdInt();
        GrdInt minorVersion = new GrdInt();
        int apiVersion = api.GrdGetApiVersion(majorVersion, minorVersion);
        System.out.printf("Loaded guardant library. Api version is: %s (%s.%s)%n", apiVersion, majorVersion.getValue(),
                          minorVersion.getValue());
    }

}
