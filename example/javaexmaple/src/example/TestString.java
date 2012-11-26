package example;

import java.lang.reflect.Field;

public class TestString {
    public static void main(String[] args) throws Exception {
        String str = "abc";
        changeString(str);
        System.err.println(str);
    }

    public static void changeString(String str) throws Exception {
        Field field = str.getClass().getDeclaredField("value");
        field.setAccessible(true);
        char[] c = {
                        'a', 'b', 'd'
        };
        field.set(str, c);
    }
}
