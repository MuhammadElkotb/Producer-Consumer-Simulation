package com.ProducerConsumerSim.ProducerConsumerSim;

import Controllers.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "Controllers")
public class ProducerConsumerSimApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerConsumerSimApplication.class, args);
	}

}
