package com.sundar.bean;

public enum OrderStatus {

	OK("Ok"), ERROR("Error"), CANCEL("Cancel");

	private String value;

	OrderStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
