package com.example.thrift.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.example.thrift.server.Echo;

public class EchoTest {
	public static void main(String[] args) throws TException {
		TTransport transport = new TSocket("127.0.0.1", 7911);
		TProtocol protocol = new TCompactProtocol(transport);
		Echo.Client client = new Echo.Client(protocol);
		transport.open();
		System.err.println(client.echo("Hello Thrift!"));
		transport.close();
	}
}
