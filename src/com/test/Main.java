package com.test;

import com.test.util.ClassJavaFileObject;
import com.test.util.SimpleJavaFileManager;
import com.test.util.StringJavaFileObject;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    static Map<String,String> CLAZZ_MAP = new HashMap();
    static List<String> CLAZZ_OPTIONS = new LinkedList<>();
    static List<JavaFileObject> COMPILATION_UNITS = new LinkedList<>();

    static void buildMap(){
        CLAZZ_MAP.clear();

        CLAZZ_MAP.put("com.test.clazz.IFace","package com.test.clazz;\n" +
                "    public interface IFace {\n" +
                "        default void writeString() {\n" +
                "            System.out.println(\"Test from [\" + getClass().getName() + \"]\");\n" +
                "        }\n" +
                "    }");

        CLAZZ_MAP.put("com.test.clazz.One","package com.test.clazz;\n" +
                "    public class One implements IFace {\n" +
                "    }");
        CLAZZ_MAP.put("com.test.clazz.Two","package com.test.clazz;\n" +
                "    public class Two implements IFace {\n" +
                "    }");
        CLAZZ_MAP.put("com.test.clazz.Three","package com.test.clazz;\n" +
                "    public class Three implements IFace {\n" +
                "    }");

        CLAZZ_OPTIONS.add("-verbose");

        CLAZZ_MAP.entrySet()
                .forEach(x -> COMPILATION_UNITS
                        .add(new StringJavaFileObject(x.getKey(), x.getValue())));
    }

    static void processMap(){
        JavaCompiler javaCompiler = javax.tools.ToolProvider.getSystemJavaCompiler();

        javaCompiler.getSourceVersions().forEach(x -> System.out.println("Java Version [" + x + "]"));

        SimpleJavaFileManager fileManager =
                new SimpleJavaFileManager(
                        javaCompiler.getStandardFileManager(null, null, null));

        List<String> clazzNameList = CLAZZ_MAP.keySet().stream()
                .map(x -> x + ".java")
                .collect(Collectors.toList());

        JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(
                new PrintWriter(System.out),
                fileManager,
                null,
                CLAZZ_OPTIONS,
                null,
                COMPILATION_UNITS
        );

        Boolean result = compilationTask.call();

        List<ClassJavaFileObject> classJavaFileObjectList = null;
        if(result)
            classJavaFileObjectList = fileManager.getGeneratedOutputFiles();
        System.out.println("Completed.");
    }

    public static void main(String args[]) {
        buildMap();
        processMap();
    }
}
