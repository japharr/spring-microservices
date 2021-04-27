package com.japharr.grpc.car.repository;

import com.japharr.grpc.car.entity.Car;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CarRepository extends ReactiveMongoRepository<Car, Long> {
    Flux<Car> findAllByDriverId(Long id);
}
