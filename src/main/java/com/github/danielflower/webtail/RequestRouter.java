package com.github.danielflower.webtail;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class RequestRouter implements AdapterEchoSocket.RequestListener {
	public static RequestRouter defaultInstance;

	private final LogCollector logCollector;

	public RequestRouter(LogCollector logCollector) {
		this.logCollector = logCollector;
	}

	@Override
	public void onRequest(WebSocketAdapter webSocket, ServiceRequest request) {
		switch (request.getName()) {
			case LogTailing:
				String logName = request.getInstanceName();
				if ("subscribe".equals(request.getAction())) {
					logCollector.subscribe(logName, webSocket);
				} else {
					logCollector.unsubscribe(logName, webSocket);
				}
				break;
			default:
				System.out.println("No request handler for " + request);

		}
	}
}
