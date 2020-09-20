package com.ssu.spec;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class helloController {

	@RequestMapping(value = "/test/hello", method = RequestMethod.POST)
	@ResponseBody
	public String hello() {
		return "Hello World";
	}
	
	@RequestMapping(value = "/test/input", method = RequestMethod.PUT)
	@ResponseBody
	public String input() {
		System.out.println("Put: input");
		return "Hello World";
	} 
	
	@RequestMapping(value = "/test/update", method = RequestMethod.POST)
	@ResponseBody
	public String update() {
		System.out.println("update");
		return "Hello World";
	}
	
	@RequestMapping(value = "/test/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete() {
		System.out.println("Delete: delete");
		return "Hello World";
	}
}