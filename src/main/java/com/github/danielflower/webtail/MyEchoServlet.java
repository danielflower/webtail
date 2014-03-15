package com.github.danielflower.webtail;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class MyEchoServlet extends WebSocketServlet {

	public MyEchoServlet() {
		System.out.println("Echo service made");
	}

	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.getPolicy().setIdleTimeout(10000);
		factory.register(AdapterEchoSocket.class);
	}
}