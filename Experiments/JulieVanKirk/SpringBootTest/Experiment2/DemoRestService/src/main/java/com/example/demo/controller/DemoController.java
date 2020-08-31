package com.example.demo.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.*;

@RestController
public class DemoController {

	@Autowired
	private ServicePerson servicePerson;
	
	ArrayList<ServicePerson> data = new ArrayList<ServicePerson>();
	People people = new People();
	int counter = 0;
	
//	@RequestMapping(value="/getPersonData")
//	public ResponseEntity<ServicePerson> getPersonData() {
//		
//		servicePerson.setId("0");
//		servicePerson.setName("Default");
//		servicePerson.setAge(0);
//		servicePerson.setHeight("0");
//		data.add(servicePerson);
//		
//		return new ResponseEntity<>(servicePerson, HttpStatus.OK);
//	}	
	
	@RequestMapping(value="/getPersonData/{id}")
	public ResponseEntity<ServicePerson> getPersonDataById(@PathVariable("id") String id) {
		for (int i = 0; i < data.size(); i ++) {
			if (id.compareTo(data.get(i).getId()) == 0) {
				return new ResponseEntity<>(data.get(i), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/getPeople")
	public ResponseEntity<ArrayList<ServicePerson>> getAllPeople() {
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/newPerson")
	public ResponseEntity<Object> newPerson() {
		data.add(newPersonGenerator());
		return new ResponseEntity<>("Person created successfully", HttpStatus.CREATED);
	}
	
	private ServicePerson newPersonGenerator() {
		ServicePerson retval = people.getArray().get(counter);
		if (counter >= people.getArray().size()) {
			counter = 0;
		} else {
			counter ++;
		}
		return retval;
	}
}
