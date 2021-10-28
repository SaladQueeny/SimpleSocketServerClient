import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;

public class test {

    public static Class getClassObject(String classstr, String className) {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        JavaFileObject file = new JavaSourceFromString(className, classstr);
        System.out.println("file:"+file);

        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

        boolean success = task.call();
        for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics())
            System.out.println(diagnostic);
        System.out.println("Success: " + success);

        if (success) {
            MyClassLoader loader = new MyClassLoader();
            Class my = loader.getClassFromFile(new File(className+".class"));
            return my;
        }

        return null;
    }

    static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

    static class MyClassLoader extends ClassLoader {

        public Class getClassFromFile(File f) {
            byte[] raw = new byte[(int) f.length()];
            System.out.println(f.length());
            InputStream in = null;
            try {
                in = new FileInputStream(f);
                in.read(raw);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return defineClass(null, raw, 0, raw.length);
        }
    }
}
