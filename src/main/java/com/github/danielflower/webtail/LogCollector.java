package com.github.danielflower.webtail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LogCollector implements LogListener {

	private final ConcurrentMap<LogInstance, List<WebSocketAdapter>> subscriptions = new ConcurrentHashMap<LogInstance, List<WebSocketAdapter>>();
	private final ConcurrentMap<String, LogInstance> instances = new ConcurrentHashMap<String, LogInstance>();
	private final Gson gson = new GsonBuilder().create();

	public LogCollector() {

	}

	public void addInstance(LogInstance logInstance) {
		subscriptions.put(logInstance, new CopyOnWriteArrayList<WebSocketAdapter>());
		instances.put(logInstance.getName(), logInstance);
		logInstance.setLogListener(this);
	}


	public void subscribe(String logName, WebSocketAdapter websocket) {
		System.out.println("Subscribing to " + logName);
		List<WebSocketAdapter> webSocketAdapters = subscriptions.get(instances.get(logName));
		if (webSocketAdapters == null) {
			throw new RuntimeException("No log with name " + logName + " exists");
		}
		webSocketAdapters.add(websocket);
	}

	public void unsubscribe(String logName, WebSocketAdapter socket) {
		System.out.println("Unsubscribing from " + logName);
		List<WebSocketAdapter> sockets = subscriptions.get(instances.get(logName));
		sockets.remove(socket);
	}

	@Override
	public void onLog(LogInstance instance, LogInstance.LogLine logLine) {
		List<WebSocketAdapter> sockets = subscriptions.get(instance);
		List<WebSocketAdapter> badSockets = new LinkedList<WebSocketAdapter>();
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
					remote.sendStringByFuture(gson.toJson(message));
				}
			} catch (Exception e) {
				System.out.println("Socket failed to write: " + e);
				badSockets.add(socket);
			}
		}
		for (WebSocketAdapter badSocket : badSockets) {
			unsubscribe(instance.getName(), badSocket);
		}
	}
}
