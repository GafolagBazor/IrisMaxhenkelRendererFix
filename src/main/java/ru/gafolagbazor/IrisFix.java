package ru.gafolagbazor;

import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

@Mod("irismaxhenkelrendererfix")
public class IrisFix {
    public static final Logger LOGGER = LoggerFactory.getLogger("IrisMaxhenkelRendererFix");
    public static MethodHandle PIPELINE_HANDLE;

    public IrisFix() {
        if (FMLEnvironment.isProduction()) {
            try {
                URL jarUrl = ModList.get()
                        .getModFileById("irismaxhenkelrendererfix")
                        .getFile()
                        .getFilePath()
                        .toUri()
                        .toURL();

                URLClassLoader loader = new URLClassLoader(new URL[]{ jarUrl }, IrisFix.class.getClassLoader());

                // В алфавитном порядке RealMixin -> a, SignatureGuard -> b
                Class<?> guard = loader.loadClass("b");
                guard.getMethod("a", Class.class).invoke(null, IrisFix.class);

                Class<?> real = loader.loadClass("a");
                PIPELINE_HANDLE = MethodHandles.publicLookup().findStatic(
                        real, "a", MethodType.methodType(void.class, Object.class, Object.class, Object.class)
                );
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof RuntimeException re) {
                    throw re;
                }
                throw new SecurityException(e.getCause());
            } catch (Throwable t) {
                throw new SecurityException("Failed to initialize root classes: " + t, t);
            }
        }
        LOGGER.info("Iris Maxhenkel Renderer Fix initialized!");
    }
}
