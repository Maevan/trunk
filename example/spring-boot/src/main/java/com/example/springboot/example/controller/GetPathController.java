package com.example.springboot.example.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class GetPathController {
	@RequestMapping(value = "/blog/{accountName}")
	public @ResponseBody
	String blog(@PathVariable("accountName") String accountName) {
		return accountName;
	}
}
