package com.github.danielflower.webtail;

import java.util.Map;

public class Message {
	private ServiceRequest.Type name;
	private String instanceName;
	private Map<String, Object> data;

	public Message() {
	}

	public Message(ServiceRequest.Type name, String instanceName, Map<String, Object> data) {
		this.name = name;
		this.instanceName = instanceName;
		this.data = data;
	}

	public ServiceRequest.Type getName() {
		return name;
	}

	public String getInstanceName() {
		return instanceName;
	}


	public Map<String, Object> getData() {
		return data;
	}
}
