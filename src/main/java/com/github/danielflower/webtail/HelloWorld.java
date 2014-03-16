package com.github.danielflower.webtail;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.resource.Resource;

import java.io.File;

public class HelloWorld {


	public static void main(String[] args) throws Exception {

		Server server = new Server();

		ServerConnector http = new ServerConnector(server);
		http.setHost("localhost");
		http.setPort(8080);
		http.setIdleTimeout(30000);
		server.addConnector(http);

		ResourceHandler resourceHandler = createResourceHandler();


		ServletHandler websocketHandler = new ServletHandler();
		server.setHandler(websocketHandler);
		websocketHandler.addServletWithMapping(MyEchoServlet.class, "/echoit");


		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[]{resourceHandler, websocketHandler, new DefaultHandler()});
		server.setHandler(handlers);

		server.start();
		server.join();
	}

	private static ResourceHandler createResourceHandler() {
		ResourceHandler resourceHandler = new ResourceHandler();
		File srcRoot = new File("src/main/html/app");
		if (srcRoot.exists()) {
			System.out.println("Development file server being used. You can update files in "
					+ srcRoot.getAbsolutePath() + " without restarting the web server.");
			resourceHandler.setResourceBase(srcRoot.getAbsolutePath());
		} else {
			resourceHandler.setBaseResource(Resource.newClassPathResource("/"));
		}
		return resourceHandler;
	}
}