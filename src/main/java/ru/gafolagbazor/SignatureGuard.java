package ru.gafolagbazor;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.CodeSigner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class SignatureGuard {
    private static final int[][] DATA = {
            {117, 23, 31, 14, 27, 119, 19, 20, 28, 117, 29, 27, 28, 21, 22, 27, 29, 107, 116, 9, 28},
            {117, 23, 31, 14, 27, 119, 19, 20, 28, 117, 29, 27, 28, 21, 22, 27, 29, 107, 116, 8, 9, 27},
            {117, 23, 31, 14, 27, 119, 19, 20, 28, 117, 29, 27, 28, 21, 22, 27, 29, 104, 116, 9, 28},
            {117, 23, 31, 14, 27, 119, 19, 20, 28, 117, 29, 27, 28, 21, 22, 27, 29, 104, 116, 8, 9, 27},
            {117, 23, 31, 14, 27, 119, 19, 20, 28, 117, 29, 27, 28, 21, 22, 27, 29, 105, 116, 9, 28},
            {117, 23, 31, 14, 27, 119, 19, 20, 28, 117, 29, 27, 28, 21, 22, 27, 29, 105, 116, 31, 25},
            {117, 23, 31, 14, 27, 119, 19, 20, 28, 117, 29, 27, 28, 21, 22, 27, 29, 110, 116, 9, 28},
            {117, 23, 31, 14, 27, 119, 19, 20, 28, 117, 29, 27, 28, 21, 22, 27, 29, 110, 116, 31, 25}
    };

    private static String d(int[] arr) {
        char[] c = new char[arr.length];
        for (int i = 0; i < arr.length; i++) {
            c[i] = (char) (arr[i] ^ 0x5A);
        }
        return new String(c);
    }

    public static void check(Class<?> anchor) {
        if (!FMLEnvironment.isProduction()) {
            return;
        }
        for (int[] row : DATA) {
            String path = d(row);
            try (InputStream stream = anchor.getResourceAsStream(path)) {
                if (stream == null || stream.read() == -1) {
                    throw new SecurityException("Missing signature file");
                }
            } catch (SecurityException e) {
                throw e;
            } catch (Exception e) {
                throw new SecurityException(e);
            }
        }

        Path jarPath = ModList.get().getModFileById("irismaxhenkelrendererfix").getFile().getFilePath();
        try (JarFile jar = new JarFile(jarPath.toFile(), true)) {
            JarEntry entry = jar.getJarEntry("b.class");
            if (entry == null) {
                throw new SecurityException("Missing entry");
            }
            try (InputStream is = jar.getInputStream(entry)) {
                is.readAllBytes();
            }
            CodeSigner[] signers = entry.getCodeSigners();
            if (signers == null || signers.length < 4) {
                throw new SecurityException("Invalid signers count: " + (signers == null ? 0 : signers.length));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e) {
            throw new SecurityException("Jar verification failed", e);
        }
    }
}
