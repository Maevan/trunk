package com.java7developer.chapter1;

import java.io.IOException;
import java.sql.SQLException;

public class MulticatchExample {
	public static void main(String[] args) {
		try {
			foo();
			bar();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void foo() throws IOException {

	}

	public static void bar() throws SQLException {

	}
}
