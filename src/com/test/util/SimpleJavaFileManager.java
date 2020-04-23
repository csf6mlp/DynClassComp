package com.test.util;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.*;

public class SimpleJavaFileManager extends ForwardingJavaFileManager {

    private final List<ClassJavaFileObject> outputFiles;

    public SimpleJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
        outputFiles = new LinkedList<>();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className,
                                               JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ClassJavaFileObject file = new ClassJavaFileObject(className, kind);
        outputFiles.add(file);
        return file;
    }

    public List<ClassJavaFileObject> getGeneratedOutputFiles() {
        return outputFiles;
    }
}

