package com.sundar.bean;

public enum Crust {

	ORIGINAL_PAN_PIZZA("Original Pan Pizza"), HAND_TOSSED("Hand Tossed"), THIN_AND_CRISPY("Thin and Crispy");

	private String value;

	Crust(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
