package com.example.springboot.example.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component("cookieController")
public class CookieController {
	@RequestMapping(value = "/setcookie", consumes = { "text/*", "application/*" }, produces = { "text/html" }, method = RequestMethod.GET)
	public String echo(@CookieValue(value = "name", defaultValue = "Please set a cookie", required = false) String name) {
		return name;
	}
}
