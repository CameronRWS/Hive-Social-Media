package com.example.demo.service;

import java.util.ArrayList;

public class People {

	ArrayList<ServicePerson> people = new ArrayList<>();
	
	ServicePerson Emily = new ServicePerson();
	ServicePerson Julie = new ServicePerson();
	ServicePerson Moriah = new ServicePerson();
	ServicePerson Gabbi = new ServicePerson();
	ServicePerson Lila = new ServicePerson();
	
	public People() {
		Emily.setId("1");
		Emily.setName("Emily");
		Emily.setAge(24);
		Emily.setHeight("5'9");
		
		Julie.setId("2");
		Julie.setName("Julie");
		Julie.setAge(24);
		Julie.setHeight("5'9");
		
		Moriah.setId("3");
		Moriah.setName("Moriah");
		Moriah.setAge(24);
		Moriah.setHeight("5'9");
		
		Gabbi.setId("3");
		Gabbi.setName("Gabbi");
		Gabbi.setAge(24);
		Gabbi.setHeight("5'9");
		
		Lila.setId("3");
		Lila.setName("Gabbi");
		Lila.setAge(24);
		Lila.setHeight("5'9");
		
		people.add(Emily);
		people.add(Julie);
		people.add(Moriah);
		people.add(Gabbi);
		people.add(Lila);
	}
	
	public ArrayList<ServicePerson> getArray() {
		return people;
	}
}
