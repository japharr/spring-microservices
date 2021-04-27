package com.japharr.grpc.driver.service;

import com.japharr.grpc.driver.dto.CarDto;
import com.japharr.grpc.driver.dto.DriverDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarServiceStub {
    public Mono<CarDto> getCarById (Long id) {
        return Mono.just(new CarDto(1L, "Toyota"));
    }

    public Flux<CarDto> getCarsByDriverId(Long id) {
        return Flux.just(new CarDto(1L, "Toyota"), new CarDto(2L, "Benz"));
    }
}
