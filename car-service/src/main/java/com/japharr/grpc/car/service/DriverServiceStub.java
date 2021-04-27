package com.japharr.grpc.car.service;

import com.japharr.grpc.car.dto.DriverDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DriverServiceStub {
    public Mono<DriverDto> getDriverById(Long id) {
        return Mono.just(new DriverDto(1L, "John"));
    }
}
