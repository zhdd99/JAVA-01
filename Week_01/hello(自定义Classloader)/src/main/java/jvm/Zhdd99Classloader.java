package jvm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author zhdd99
 */
public class Zhdd99Classloader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Object hello = new Zhdd99Classloader().findClass("Hello").newInstance();
            Method method = hello.getClass().getDeclaredMethod("hello");
            method.invoke(hello);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = name + ".xlass";
        try (InputStream is = new FileInputStream(path)) {
            int length = is.available();
            byte[] bytes = new byte[length];
            is.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte)(255 - bytes[i]);
            }
            return defineClass(name, bytes, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
