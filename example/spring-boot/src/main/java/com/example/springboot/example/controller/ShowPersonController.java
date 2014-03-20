package com.example.springboot.example.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springboot.example.bean.Person;

@Controller
@Component
public class ShowPersonController {
	@RequestMapping("/showPerson")
	public @ResponseBody
	Person showPerson() {
		return new Person("Lv9", "q153996072@gmail.com");
	}
}
