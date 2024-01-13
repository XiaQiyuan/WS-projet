package com.iris.service;

import javax.xml.ws.Endpoint;
 
import com.iris.service.HelloWSImpl;
 


public class ServerTest {
	 
	public static void main(String[] args) {
		String address = "http://127.0.0.1:8989/iris/hellows";
		Endpoint.publish(address, new HelloWSImpl());
		System.out.println("success!");
	}
}
