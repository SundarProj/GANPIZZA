package com.sundar.bean;

public enum Sauce {

	CLASSIC_MARINARA("Classic Marinara"), GARLIC_PARMESAN("Garlic Parmesan"), BARBEQUE("Barbeque"), BUFFALO("Buffalo");

	private String value;

	Sauce(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
