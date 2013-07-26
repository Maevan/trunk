package com.example.websocket.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@WebServlet(name = "example", urlPatterns = { "/example" })
public class ExampleWebSocketServlet extends WebSocketServlet {

	@Override
	public void init() throws ServletException {
		new Thread() {
			public void run() {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {}
					if (USERS.isEmpty()) {
						continue;
					}
					StringBuilder users = new StringBuilder();
					for (String name : USERS.values()) {
						users.append(name).append(",");
					}
					users.deleteCharAt(users.length() - 1);
					for (WsOutbound client : CLIENTS.values()) {
						try {
							client.writeTextMessage(new Message().append("{\"type\" : \"userlist\",\"detail\" : \"").append(users.toString()).append("\"}").getData());
							client.flush();
						} catch (IOException e) {
							LOGGER.error("输出用户列表时失败", e);
						}
					}
				}
			}
		}.start();
	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, final HttpServletRequest request) {
		final String name = request.getParameter("name");

		return new MessageInbound() {
			private String id = null;

			@Override
			protected void onClose(int status) {
				LOGGER.info("stream close status is [" + status + "]");
				CLIENTS.remove(id);
				USERS.remove(id);
			}

			@Override
			protected void onOpen(org.apache.catalina.websocket.WsOutbound outbound) {
				id = UUID.randomUUID().toString();

				LOGGER.info("stream is open");
				CLIENTS.put(id, outbound);
				USERS.put(id, name);

				for (WsOutbound client : CLIENTS.values()) {
					try {
						client.writeTextMessage(new Message().append("{\"type\" : \"message\",\"name\" : \"").append(name).append("\",\"detail\" : \"").append("我来加入你们的聊天").append("\"}").getData());
						client.flush();
					} catch (IOException e) {
						LOGGER.error("输出用户列表时失败", e);
					}
				}
			}

			@Override
			protected void onTextMessage(CharBuffer buffer) throws IOException {
				for (WsOutbound client : CLIENTS.values()) {
					client.writeTextMessage(new Message().append("{\"type\" : \"message\",\"name\" : \"").append(name).append("\",\"detail\" : \"").append(buffer.toString()).append("\"}").getData());
					client.flush();
				}
			}

			@Override
			protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
				// TODO Nothing
			}
		};
	}

	private static class Message {
		Message() {
		}

		Message append(String value) {
			data.append(value);
			return this;
		}

		CharBuffer getData() {
			data.insert(0, "~~##");
			data.append("##~~");
			return CharBuffer.wrap(data);
		}

		private StringBuilder data = new StringBuilder();
	}

	// 常量属性
	/** 日志对象 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleWebSocketServlet.class);

	/** 用户列表 */
	private static final java.util.concurrent.ConcurrentMap<String, String> USERS = new ConcurrentHashMap<String, String>();

	/** 当前聊天室内人员 */
	private static final java.util.concurrent.ConcurrentMap<String, WsOutbound> CLIENTS = new ConcurrentHashMap<String, WsOutbound>();
}
