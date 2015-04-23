package edu.upc;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AppInit {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		System.out.println("Esto crea las tablas en forma automatica...Todas las que encuentre en carpeta upc.edu.model");
		
		context.close();
	}

}
