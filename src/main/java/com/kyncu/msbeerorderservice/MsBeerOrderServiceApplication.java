package com.kyncu.msbeerorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;

@SpringBootApplication(exclude = ArtemisAutoConfiguration.class)
public class MsBeerOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBeerOrderServiceApplication.class, args);
	}

}
