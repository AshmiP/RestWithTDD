package com.yash.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)

public class FirstSpringBootRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringBootRestApplication.class, args);
	}

}
