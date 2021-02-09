package com.sundar.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BakedServerResponse {

	private GANPizza pizza;
	private String startingThread;
	private String completingThread;
	private long timeMs;
	private boolean error = false;

	public GANPizza getPizza() {
		return pizza;
	}

	public void setPizza(GANPizza pizza) {
		this.pizza = pizza;
	}

	public String getStartingThread() {
		return startingThread;
	}

	public void setStartingThread(String startingThread) {
		this.startingThread = startingThread;
	}

	public String getCompletingThread() {
		return completingThread;
	}

	public void setCompletingThread(String completingThread) {
		this.completingThread = completingThread;
	}

	public long getTimeMs() {
		return timeMs;
	}

	public void setTimeMs(long timeMs) {
		this.timeMs = timeMs;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
