package com.iris.service;
import javax.jws.WebMethod;
import javax.jws.WebService;
 

@WebService
public interface HelloWS {
 
	@WebMethod
	public String sayHello(String name);
}

