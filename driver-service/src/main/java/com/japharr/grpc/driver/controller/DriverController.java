package com.japharr.grpc.driver.controller;

import com.japharr.grpc.driver.dto.CarDto;
import com.japharr.grpc.driver.dto.DriverDto;
import com.japharr.grpc.driver.entity.Driver;
import com.japharr.grpc.driver.repository.DriverRepository;
import com.japharr.grpc.driver.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/drivers")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DriverDto> addDriver(@RequestBody Driver car) {
        return driverService.addDriver(car);
    }

    @GetMapping("/drivers")
    public Flux<DriverDto> getDrivers() {
        return driverService.getDrivers();
    }

    @GetMapping("/drivers/{id}/cars")
    public Flux<CarDto> getDrivers(@PathVariable("id") Long driverId) {
        return driverService.getDriversCarById(driverId);
    }

    @DeleteMapping("/drivers/{id}")
    public Mono<ResponseEntity<Void>> deleteDriver(@PathVariable("id") Long id) {
        return driverService.deleteDriver(id)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
