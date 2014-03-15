package com.github.danielflower.webtail;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * Example EchoSocket using Adapter.
 */
public class AdapterEchoSocket extends WebSocketAdapter {

	@Override
	public void onWebSocketText(String message) {
		try {
			System.out.printf("Echoing back message [%s]%n", message);
			while (isConnected()) {
				getRemote().sendString(System.currentTimeMillis() + ": " + message);
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}