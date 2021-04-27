package com.japharr.grpc.car;

import com.japharr.grpc.car.entity.Car;
import com.japharr.grpc.car.repository.CarRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class CarServiceStarter {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceStarter.class, args);
	}

	@Bean
	ApplicationRunner init(CarRepository repository) {
		// Electric VWs from https://www.vw.com/electric-concepts/
		// Release dates from
		// https://www.motor1.com/features/346407/volkswagen-id-price-on-sale/
		Car ID = new Car(1L, "ID.", 1L, LocalDate.of(2019, Month.DECEMBER, 1));
		Car ID_CROZZ = new Car(2L, "ID. CROZZ", 2L, LocalDate.of(2021, Month.MAY, 1));
		Car ID_VIZZION = new Car(3L, "ID. VIZZION", 1L, LocalDate.of(2021, Month.DECEMBER, 1));
		Car ID_BUZZ = new Car(4L, "ID. BUZZ", 2L, LocalDate.of(2021, Month.DECEMBER, 1));
		Car ID_FUZZ = new Car(5L, "ID. FUZZ", 1L, LocalDate.of(2021, Month.JANUARY, 1));
		Set<Car> vwConcepts = Set.of(ID, ID_BUZZ, ID_CROZZ, ID_VIZZION, ID_FUZZ);

		return args -> {
			repository.deleteAll().thenMany(Flux.just(vwConcepts).flatMap(repository::saveAll))
				.thenMany(repository.findAll()).subscribe(car -> System.out.println("saving " + car.toString()));
		};
	}

}
