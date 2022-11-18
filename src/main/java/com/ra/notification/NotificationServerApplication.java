package com.ra.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableEurekaClient
public class NotificationServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(NotificationServerApplication.class, args);
	}

}
