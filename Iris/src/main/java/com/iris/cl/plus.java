package com.iris.cl;

public class CheckUserInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
 
	public CheckUserInterceptor() {
		super(Phase.PRE_PROTOCOL);
	}
	

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		Header header = message.getHeader(new QName("atguigu"));
	    if (header!=null) {
		  Element atguiguEle = (Element) header.getObject();
		  String name = atguiguEle.getElementsByTagName("name").item(0).getTextContent();
		  String password = atguiguEle.getElementsByTagName("password").item(0).getTextContent();
	      if ("xfzhang".equals(name)&&"123456".equals(password)) {
			System.out.println("server 通过拦截器...");
		    return;
	      }		  
	    }
	    //不能通过
	    System.out.println("server 没有通过拦截器....");
	    throw new Fault(new RuntimeException("error!"));
	}
