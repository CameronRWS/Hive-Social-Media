package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.*;

@RestController
public class DemoController {

	@Autowired
	private ServicePerson servicePerson;
	
	@RequestMapping(value="/getPersonData", method=RequestMethod.GET)
	public ServicePerson getPersonData() {
		
		servicePerson.setId("2");
		servicePerson.setName("Julie");
		servicePerson.setAge("20");
		servicePerson.setHeight("5'4");
		
		return servicePerson;
	}
}
