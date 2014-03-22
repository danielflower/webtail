package com.github.danielflower.webtail;

public class ServiceRequest {
	private Type name;
	private String action;
	private String instanceName;

	public ServiceRequest() {
	}

	public ServiceRequest(Type name, String action, String instanceName) {
		this.name = name;
		this.action = action;
		this.instanceName = instanceName;
	}

	public Type getName() {
		return name;
	}

	public String getAction() {
		return action;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public enum Type {
		LogTailing
	}
}
