package example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyExample {
	public static void main(String[] args) {
		T1 t1 = (T1) Proxy.newProxyInstance(T1.class.getClassLoader(), T1.class.getInterfaces(), new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}

interface I1 {

}

interface I2 {

}

class T1 implements I1, I2 {

}