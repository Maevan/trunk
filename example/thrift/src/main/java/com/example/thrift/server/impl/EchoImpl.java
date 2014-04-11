package com.example.thrift.server.impl;

import org.apache.thrift.TException;

import com.example.thrift.server.Echo;

public class EchoImpl implements Echo.Iface {
	@Override
	public String echo(String s) throws TException {
		return s;
	}
}
