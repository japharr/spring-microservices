package com.japharr.grpc.car.service;

import com.japharr.grpc.car.dto.DriverDto;
import org.mvnsearch.rsocket.loadbalance.RSocketServiceRegistry;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DriverServiceStub {
    private final Mono<RSocketRequester> rSocketRequester;

    public DriverServiceStub(RSocketRequester.Builder rsocketRequesterBuilder, ReactiveDiscoveryClient discoveryClient, RSocketServiceRegistry rsocketServiceRegistry) {
        this.rSocketRequester = Mono.just(rsocketServiceRegistry.
            buildLoadBalanceRSocket("driver-service", rsocketRequesterBuilder));
    }

    public Mono<DriverDto> getDriverById(Long id) {
        //return Mono.just(new DriverDto(1L, "John"));
        return this.rSocketRequester.flatMap(req ->
            req.route("request-response")
                //.metadata(token.getTokenValue(), BEARER_AUTHENTICATION_MIME_TYPE)
                .data(id).retrieveMono(DriverDto.class)
        );
    }
}
