package com.java7developer.example;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlerTest {
	static class Foo {
		public void println(String str) {
			System.out.println(str);
		}
	}

	public static MethodHandle getPrintlnMethodHandle(Object reveiver) throws Throwable {
		MethodType mt = MethodType.methodType(void.class, String.class);

		return MethodHandles.lookup().findVirtual(reveiver.getClass(), "println", mt).bindTo(reveiver);
	}

	public static void main(String[] args) throws Throwable {
		getPrintlnMethodHandle(System.out).invokeExact("Hello MethodHandle");
		getPrintlnMethodHandle(new Foo()).invokeExact("Hello MethodHandle");
	}
}
