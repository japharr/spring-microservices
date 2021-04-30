package com.japharr.grpc.driver;

import com.japharr.grpc.driver.entity.Driver;
import com.japharr.grpc.driver.repository.DriverRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import reactor.core.publisher.Flux;

import java.util.Set;

@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
//@SpringBootApplication(exclude = {ReactiveUserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class, ReactiveSecurityAutoConfiguration.class, RSocketSecurityAutoConfiguration.class})
public class DriverServiceStarter {

	public static void main(String[] args) {
		SpringApplication.run(DriverServiceStarter.class, args);
	}

	@Bean
	ApplicationRunner init(DriverRepository repository) {
		// Electric VWs from https://www.vw.com/electric-concepts/
		// Release dates from
		// https://www.motor1.com/features/346407/volkswagen-id-price-on-sale/
		Driver john = new Driver(1L, "John");
		Driver james = new Driver(2L, "James");
		Driver jake = new Driver(3L, "Jake");
		Set<Driver> vwConcepts = Set.of(john, james, jake);

		return args -> {
			repository.deleteAll().thenMany(Flux.just(vwConcepts).flatMap(repository::saveAll))
				.thenMany(repository.findAll()).subscribe(car -> System.out.println("saving " + car.toString()));
		};
	}
}
