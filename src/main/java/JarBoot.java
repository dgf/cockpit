import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Comparator;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Embeddable boot wrapper for a fat jar that includes the required classpath dependencies.
 *
 * @link http://qdolan.blogspot.com/2008/10/embedded-jar-classloader-in-under-100.html
 * @link http://www.jdotsoft.com/download/JarClassLoader.java
 */
public class JarBoot extends URLClassLoader {

    private static final String BOOT_MAIN_CLASS = "Boot-Main-Class";

    private String mainClassName;

    private JarBoot() throws IOException {
        super(((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs(),
                Thread.currentThread().getContextClassLoader());

        // load root jar with decoded location URL
        CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();
        File rootFile = new File(URLDecoder.decode(codeSource.getLocation().getFile(), "UTF-8"));
        addURL(rootFile.toURI().toURL());

        // get and check the Manifest
        JarFile rootJarFile = new JarFile(rootFile);
        Attributes attributes = rootJarFile.getManifest().getMainAttributes();
        mainClassName = attributes.getValue(BOOT_MAIN_CLASS);
        if (mainClassName == null) {
            throw new IllegalStateException("jar without " + BOOT_MAIN_CLASS + " attribute");
        }
        String classPath = attributes.getValue(Attributes.Name.CLASS_PATH);
        if (classPath == null) {
            throw new IllegalStateException("jar without " + Attributes.Name.CLASS_PATH + " attribute");
        }

        // copy jars to temporary folder with flattened file names and add the URLs to the loader itself
        Path tempPath = Files.createTempDirectory(rootFile.getName() + "_");
        for (String path : classPath.split(" ")) {
            JarEntry entry = rootJarFile.getJarEntry(path);
            if (entry.isDirectory() || !entry.getName().toLowerCase().endsWith(".jar")) {
                throw new IllegalStateException("invalid classpath reference: " + path);
            }
            try (InputStream input = rootJarFile.getInputStream(entry)) {
                Path target = Paths.get(tempPath.toString(), entry.getName().replace('/', '_'));
                Files.copy(input, target);
                addURL(target.toUri().toURL());
            }
        }

        // register a shutdown hock to recursive remove temporary jars and the folder itself
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.walk(tempPath).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(f -> {
                    if (!f.delete()) {
                        throw new IllegalStateException("temporary removing failed: " + f.getAbsolutePath());
                    }
                });
            } catch (IOException e) {
                throw new IllegalStateException("temporary path cleanup failed: " + tempPath);
            }
        }, "Shutdown-" + JarBoot.class.getName()));
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            Class<?> clazz = findLoadedClass(name);
            if (clazz == null) {
                clazz = findClass(name);
                if (resolve) {
                    resolveClass(clazz);
                }
            }
            return clazz;
        } catch (ClassNotFoundException e) {
            return super.loadClass(name, resolve);
        }
    }

    @SuppressWarnings("unchecked")
    private void invoke(String[] args)
            throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        Method method = loadClass(mainClassName).getMethod("main", String[].class);
        int modifiers = method.getModifiers();
        method.setAccessible(true);
        if (method.getReturnType() != void.class || !Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
            throw new NoSuchMethodException("public static void main(String[] args)");
        }
        method.invoke(null, new Object[]{args});
    }

    public static void main(String[] args) {
        try {
            new JarBoot().invoke(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}