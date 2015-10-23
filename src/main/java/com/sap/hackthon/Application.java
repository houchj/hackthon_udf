package com.sap.hackthon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.sap.hackthon")
@PropertySources({
	@PropertySource("classpath:application.properties"),
	@PropertySource("classpath:application_local.properties")
})
public class Application{

    public static void main(String[] args) {
    	String env = System.getProperty("app.env");
		if(env == null || env.equals("")) {
			System.setProperty("app.env", "local");
		}
		SpringApplication.run(Application.class, args);
    }

}