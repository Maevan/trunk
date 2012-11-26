package example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Person5 {
    public static void main(String[] args) {
        Method[] methods = Person5.class.getDeclaredMethods();
        for (Method field : methods) {
            System.err.println(field.getName());
        }
    }

    private static class NullPerson {
        private NullPerson() {

        }

        private void test() {}
    }

    static NullPerson person5 = new NullPerson();

    static {
        person5.test();
    }
}