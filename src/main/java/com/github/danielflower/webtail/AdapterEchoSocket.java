package com.github.danielflower.webtail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class AdapterEchoSocket extends WebSocketAdapter {

	private final RequestListener requestListener;
	private final Gson gson = new GsonBuilder().create();


	public AdapterEchoSocket() {
		this(RequestRouter.defaultInstance);
	}

	public AdapterEchoSocket(RequestListener requestListener) {
		this.requestListener = requestListener;
	}

	@Override
	public void onWebSocketText(String requestJson) {
		System.out.printf("Got message [%s]%n", requestJson);
		ServiceRequest request = gson.fromJson(requestJson, ServiceRequest.class);
		requestListener.onRequest(this, request);
	}

	public interface RequestListener {
		public void onRequest(WebSocketAdapter adapterEchoSocket, ServiceRequest request);
	}


}