package com.iris.service;

import javax.jws.WebService;
 

@WebService
public class HelloWSImpl implements HelloWS {
 
	public String sayHello(String name) {
		System.out.println("server sayHello()"+name);
		return "hello "+name;
	}
 
}