package com.gomezvaez.eventsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EventSourcingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourcingApplication.class, args);
	}

}
