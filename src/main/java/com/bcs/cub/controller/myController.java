package com.bcs.cub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class myController {

	@RequestMapping(method = RequestMethod.GET,value = "/test")
	public String test() {
		return "test cub";
	}
}
