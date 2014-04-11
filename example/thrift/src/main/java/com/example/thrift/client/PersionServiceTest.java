package com.example.thrift.client;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.example.thrift.bean.Persion;
import com.example.thrift.server.PersionService;

public class PersionServiceTest {

	public static void main(String[] args) throws Exception {
		TTransport transport = new TSocket("127.0.0.1", 7911);
		TProtocol protocol = new TCompactProtocol(transport);
		PersionService.Client client = new PersionService.Client(protocol);
		transport.open();
		Persion persion = client.get("1");
		System.err.println("Persion Name: " + persion.getName());
		client.insert(persion);
		transport.close();
	}
}
