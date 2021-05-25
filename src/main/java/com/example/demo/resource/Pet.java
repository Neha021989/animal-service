package com.example.demo.resource;

public class Pet {

	private String name;
	private String type;

	public Pet(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setType() {
		this.type = type;
	}

	public void setName() {
		this.name = name;
	}
}
