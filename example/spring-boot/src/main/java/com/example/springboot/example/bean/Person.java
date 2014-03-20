package com.example.springboot.example.bean;

import java.util.Date;


public class Person {
	private String name;
	private String email;
	private Date birthday;

	public Person(){
		
	}
	
	public Person(String name, String email) {
		this.name = name;
		this.email = email;
		this.birthday = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
