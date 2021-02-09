package com.sundar.bean;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GANPizza {
	private long number;
	private Crust crust;
	private Sauce sauce;
	List<Topping> toppings;
	private OrderStatus status;
	private String message;

	public List<Topping> getToppings() {
		return toppings;
	}

	public void setToppings(List<Topping> toppings) {
		this.toppings = toppings;
	}

	public Crust getCrust() {
		return crust;
	}

	public void setCrust(Crust crust) {
		this.crust = crust;
	}

	public Sauce getSauce() {
		return sauce;
	}

	public void setSauce(Sauce sauce) {
		this.sauce = sauce;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

}
