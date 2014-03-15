package com.github.danielflower.webtail;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.resource.Resource;

public class HelloWorld
{


	public static void main(String[] args) throws Exception
	{

		System.out.println(Class.forName("org.eclipse.jetty.websocket.server.WebSocketServerFactory"));

		Server server = new Server();

		ServerConnector http = new ServerConnector(server);
		http.setHost("localhost");
		http.setPort(8080);
		http.setIdleTimeout(30000);
		server.addConnector(http);

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setBaseResource(Resource.newClassPathResource("webroot"));


		ServletHandler websocketHandler = new ServletHandler();
		server.setHandler(websocketHandler);
		websocketHandler.addServletWithMapping(MyEchoServlet.class, "/echoit");


		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resourceHandler, websocketHandler, new DefaultHandler() });
		server.setHandler(handlers);

		server.start();
		server.join();
	}
}