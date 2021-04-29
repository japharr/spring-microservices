package com.japharr.grpc.car.controller;

import com.japharr.grpc.car.dto.CarDto;
import com.japharr.grpc.car.service.CarService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class CarRSocketController {
    private final CarService carService;

    public CarRSocketController(CarService carService) {
        this.carService = carService;
    }

    @MessageMapping("request-response")
    Mono<CarDto> requestResponse(Long carId) {
        //log.info("Received request-response request: {}", request);
        //log.info("Request-response initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());
        // create a single Message and return it
        return carService.findCarById(carId);
    }
}
