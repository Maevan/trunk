package com.java7developer.example;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokeDynamicTest {
	public static void testMethod(String s) {
		System.out.println("Hello Invoke Dynamic " + s);
	}

	public static CallSite bootstrapMethod(MethodHandles.Lookup lookup, Class<?> clazz, String name, MethodType mt) throws Throwable {
		return new ConstantCallSite(lookup.findStatic(clazz, name, mt));
	}

	public static MethodHandle mhBootstrapMethod() throws Throwable {
		return MethodHandles.lookup().findStatic(InvokeDynamicTest.class, "bootstrapMethod", DEFAULT_METHOD_TYPE);
	}

	public static MethodHandle indyBootstrapMethod() throws Throwable {
		CallSite cs = (CallSite) mhBootstrapMethod().invokeWithArguments(MethodHandles.lookup(), InvokeDynamicTest.class, "testMethod",
				MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", Thread.currentThread().getContextClassLoader()));

		return cs.dynamicInvoker();
	}

	public static void main(String[] args) throws Throwable {
		indyBootstrapMethod().invokeExact("Lv9");
	}

	private static final MethodType DEFAULT_METHOD_TYPE;

	static {
		DEFAULT_METHOD_TYPE = MethodType.fromMethodDescriptorString(
				"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", Thread.currentThread().getContextClassLoader());
	}
}
