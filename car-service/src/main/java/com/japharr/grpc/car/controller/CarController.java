package com.japharr.grpc.car.controller;

import com.japharr.grpc.car.dto.CarDto;
import com.japharr.grpc.car.dto.DriverDto;
import com.japharr.grpc.car.entity.Car;
import com.japharr.grpc.car.repository.CarRepository;
import com.japharr.grpc.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CarDto> addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @GetMapping("/cars")
    public Flux<CarDto> getCars() {
        return carService.findAllCars();
    }

    @GetMapping("/cars/{id}")
    public Mono<CarDto> findCarById(@PathVariable("id") Long id) {
        return carService.findCarById(id);
    }

    @GetMapping("/cars/{id}/drivers/{driverId}")
    public Mono<DriverDto> findCarById(@PathVariable("id") Long id, @PathVariable("driverId") Long driverId) {
        return carService.findDriverById(driverId);
    }

    @DeleteMapping("/cars/{id}")
    public Mono<ResponseEntity<Void>> deleteCar(@PathVariable("id") Long id) {
        return carService.deleteCarById(id)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
