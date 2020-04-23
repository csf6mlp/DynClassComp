package com.test.util;

import javax.tools.SimpleJavaFileObject;
import java.io.*;
import java.net.URI;

public class StringJavaFileObject extends SimpleJavaFileObject {
    private final String code;

    public StringJavaFileObject(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
}
