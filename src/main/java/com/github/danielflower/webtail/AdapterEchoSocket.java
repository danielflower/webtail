package com.github.danielflower.webtail;

import java.io.IOException;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * Example EchoSocket using Adapter.
 */
public class AdapterEchoSocket extends WebSocketAdapter {

	@Override
	public void onWebSocketText(String message) {
		if (isConnected()) {
			try {
				System.out.printf("Echoing back message [%s]%n", message);
				getRemote().sendString(message);
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}
	}
}