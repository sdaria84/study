package com.javanbeyond;

import org.apache.camel.CamelContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Please pass java or xml as argument");
			System.exit(1);
		}
		if (args[0].equalsIgnoreCase("java")) {
			// executeJavaDSL("getSendMessageQueue");
			executeJavaDSL("usePoolingInActiveMQ");
			//executeJavaDSL("usingMessageListenerWithActiveMQ");
		} else if (args[0].equalsIgnoreCase("xml")) {
			// executeSpringDSL("getSendMessageQueue");
			//executeSpringDSL("usePoolingInActiveMQ");
			// executeSpringDSL("usingMessageListenerWithActiveMQ");
		} else {
			System.out.println("Argument not supported.");
			System.exit(1);
		}
		while (true) {

		}
	}

	@SuppressWarnings("resource")
	/*
	public static void executeSpringDSL(String routeId) throws Exception {
		// Starting specific route of xml
		new ClassPathXmlApplicationContext("camel-context.xml")
				.getBean("camel", CamelContext.class).startRoute(routeId);
	}
	*/

	public static void executeJavaDSL(String routeId) throws Exception {
		new CamelJavaDSL().getCamelContext().startRoute(routeId);
	}

}
