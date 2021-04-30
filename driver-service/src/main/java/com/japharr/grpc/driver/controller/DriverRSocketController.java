package com.japharr.grpc.driver.controller;

import com.japharr.grpc.driver.dto.DriverDto;
import com.japharr.grpc.driver.service.DriverService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class DriverRSocketController {
    private final DriverService driverService;

    public DriverRSocketController(DriverService driverService) {
        this.driverService = driverService;
    }

    @MessageMapping("request-response")
    Mono<DriverDto> requestResponse(Long driverId) {
        //log.info("Received request-response request: {}", request);
        //log.info("Request-response initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());
        // create a single Message and return it
        return driverService.getDriver(driverId);
    }
}
