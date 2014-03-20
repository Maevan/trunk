package com.example.springboot.example.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.example.bean.Person;

@RestController
@Component
public class ModelValueController {
	
	@RequestMapping(value = { "/generate" })
	public Person generate(@ModelAttribute(value = "persion") Person person) {
		return person;
	}
}
