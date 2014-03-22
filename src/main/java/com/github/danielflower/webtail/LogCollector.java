package com.github.danielflower.webtail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
public class LogCollector implements LogListener {

	private final Map<LogInstance, List<WebSocketAdapter>> subscriptions = new HashMap<LogInstance, List<WebSocketAdapter>>();
	private final Map<String, LogInstance> instances = new HashMap<String, LogInstance>();
	private final Gson gson = new GsonBuilder().create();

	public LogCollector() {

	}

	public void addInstance(LogInstance logInstance) {
		synchronized (subscriptions) {
			subscriptions.put(logInstance, new LinkedList<WebSocketAdapter>());
			instances.put(logInstance.getName(), logInstance);
			logInstance.setLogListener(this);
		}
	}


	public void subscribe(String logName, WebSocketAdapter websocket) {
		System.out.println("Subscribing to " + logName);
		List<WebSocketAdapter> webSocketAdapters;
		synchronized (subscriptions) {
			webSocketAdapters = subscriptions.get(instances.get(logName));
			if (webSocketAdapters == null) {
				throw new RuntimeException("No log with name " + logName + " exists");
			}

		}
		synchronized (webSocketAdapters) {
			webSocketAdapters.add(websocket);
		}
	}
	public void unsubscribe(String logName, WebSocketAdapter socket) {
		System.out.println("Unsubscribing from " + logName);
		synchronized (subscriptions) {
			List<WebSocketAdapter> sockets = subscriptions.get(instances.get(logName));
			sockets.remove(socket);
		}
	}

	@Override
	public void onLog(LogInstance instance, LogInstance.LogLine logLine) {
		List<WebSocketAdapter> sockets;
		synchronized (subscriptions) {
			sockets = subscriptions.get(instance);
		}
		List<WebSocketAdapter> badSockets = new LinkedList<WebSocketAdapter>();
		synchronized (sockets) {
			for (WebSocketAdapter socket : sockets) {
				try {
					RemoteEndpoint remote = socket.getRemote();
					if (remote == null) {
						System.out.println("Can't send; remote null");
						badSockets.add(socket);
					} else {
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("number", logLine.getNumber());
						data.put("value", logLine.getValue());
						Message message = new Message(ServiceRequest.Type.LogTailing, instance.getName(), data);
						remote.sendString(gson.toJson(message));
					}
				} catch (Exception e) {
					System.out.println("Socket failed to write: " + e);
					badSockets.add(socket);
				}
			}
		}
		for (WebSocketAdapter badSocket : badSockets) {
			unsubscribe(instance.getName(), badSocket);
		}
	}
}
