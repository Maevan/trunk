package example;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorTester {
    public static void main(String[] args) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException,
                    InvocationTargetException {
        Class<?> clazz = Class.forName("example.ConstructorTesterData");
        Constructor<?> constructor = clazz.getDeclaredConstructor();// 获取无参构造器
        Constructor<?> argconstructor = clazz.getDeclaredConstructor(String.class);// 指定具体的参数构造器

        constructor.setAccessible(true);
        argconstructor.setAccessible(true);
 
        ConstructorTesterData data = (ConstructorTesterData) constructor.newInstance(null);
        data = (ConstructorTesterData) argconstructor.newInstance("Lv9");
        System.err.println(data.getName());
    }
}

class ConstructorTesterData {
    private String name;

    private ConstructorTesterData() {

    }

    private ConstructorTesterData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
