package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class ServicePerson {
	
	private String id;
	private String name;
	private String age;
	private String height;
	
	public void setId(String arg) {
		this.id = arg;
		return;
	}
	
	public void setName(String arg) {
		this.name = arg;
		return;
	}
	
	public void setAge(String arg) {
		this.age = arg;
		return;
	}
	
	public void setHeight(String arg) {
		this.height = arg;
		return;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAge() {
		return age;
	}
	
	public String getHeight() {
		return height;
	}
	
	public String getId() {
		return id;
	}

}
