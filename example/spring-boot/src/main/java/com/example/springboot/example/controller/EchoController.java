package com.example.springboot.example.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Component("echoController")
public class EchoController {
	@RequestMapping(params = { "name" }, value = "/echo", consumes = { "text/*", "application/*" }, produces = { "text/html" }, method = RequestMethod.GET)
	public @ResponseBody
	String echo(@RequestParam(value = "name", defaultValue = "please say something...") String name) {
		return name;
	}
}
