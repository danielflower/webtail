package com.github.danielflower.webtail;

public interface LogListener {
	void onLog(LogInstance instance, LogInstance.LogLine logLine);
}
