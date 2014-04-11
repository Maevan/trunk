package com.example.thrift.server.impl;

import org.apache.thrift.TException;

import com.example.thrift.bean.Persion;
import com.example.thrift.server.PersionService.Iface;

public class PersionServiceImpl implements Iface {

	@Override
	public Persion get(String id) throws TException {
		System.err.println("ID: " + id);
		return new Persion("Lv9", 24, false);
	}

	@Override
	public void insert(Persion persion) throws TException {
		System.err.println("Name: " + persion.getName() + ",Age: " + persion.getAge() + ",isOnSchool: " + persion.isOnSchool());
	}
}
