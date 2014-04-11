package com.example.thrift.server;

import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.example.thrift.server.impl.EchoImpl;
import com.example.thrift.server.impl.PersionServiceImpl;

public class Server {
	public static void main(String[] args) throws Exception {
		TServerSocket ss = new TServerSocket(7911);
		TProtocolFactory factory = new TCompactProtocol.Factory();// 传输协议风格

		TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(ss).inputProtocolFactory(factory).outputProtocolFactory(factory).processor(new Echo.Processor<Echo.Iface>(new EchoImpl()))
				.processor(new PersionService.Processor<PersionService.Iface>(new PersionServiceImpl())));
		server.serve();
	}
}
