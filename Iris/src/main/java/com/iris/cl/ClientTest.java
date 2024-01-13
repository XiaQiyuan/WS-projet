package com.iris.cl;

import com.iris.service.HelloWSImpl;
import com.iris.service.HelloWSImplService;
 

public class ClientTest {
 
	public static void main(String[] args) {
		HelloWSImplService factory = new HelloWSImplService();
		HelloWSImpl helloWS = factory.getHelloWSImplPort();
		System.out.println(helloWS.getClass());
		
		String result = helloWS.sayHello("Jack");
		System.out.println("client "+result);
	}
}