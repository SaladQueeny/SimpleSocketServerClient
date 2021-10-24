import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class test {
    public static Class getClassObject(String classstr) throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        JavaFileObject file = new JavaSourceFromString("test1", classstr);
        System.out.println(file);

        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

        boolean success = task.call();
        for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics())
            System.out.println(diagnostic);
        System.out.println("Success: " + success);
//        List<Double> x = new ArrayList<>();
//        x.add(1.0);
//        x.add(2.0);
//        x.add(3.0);
//        List<Double> y = new ArrayList<>();
//        y.add(1.0);
//        y.add(2.0);
//        y.add(3.0);
//        List<Double> t = new ArrayList<>();
//        t.add(1.0);
//        t.add(2.0);
//        t.add(3.0);

        if (success) {

            MyClassLoader loader = new MyClassLoader();

            Class my = loader.getClassFromFile(new File("test1.class"));
            return my;
//            Method m = my.getMethod("test", new Class[] { List.class, List.class,List.class });
//            Object o = my.newInstance();
//            List<List<List<Double>>> result = (List<List<List<Double>>>) m.invoke(o, new Object[] { x, y, t });
//            System.out.println(result);

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
