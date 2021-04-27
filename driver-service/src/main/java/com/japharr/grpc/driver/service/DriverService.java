package com.japharr.grpc.driver.service;

import com.japharr.grpc.driver.dto.CarDto;
import com.japharr.grpc.driver.dto.DriverDto;
import com.japharr.grpc.driver.entity.Driver;
import com.japharr.grpc.driver.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarServiceStub carServiceStub;

    public Flux<DriverDto> getDrivers() {
        return driverRepository.findAll()
            .map(r -> new DriverDto(r.getId(), r.getName()));
    }

    public Mono<DriverDto> addDriver(Driver car) {
        return driverRepository.save(car)
            .map(r-> new DriverDto(r.getId(), r.getName()));
    }

    public Mono<Void> deleteDriver(@PathVariable("id") Long id) {
        return driverRepository.findById(id)
            .flatMap(car -> driverRepository.delete(car));
    }

    public Flux<CarDto> getDriversCarById(Long id) {
        return Mono.just(id)
            .flatMapMany(r -> carServiceStub.getCarsByDriverId(id))
            .map(r -> new CarDto(r.getId(), r.getName()));
    }

    public Mono<CarDto> getCars(Long id) {
        return Mono.just(id)
            .flatMap(r -> carServiceStub.getCarById(id))
            .map(r -> new CarDto(r.getId(), r.getName()));
    }
}
