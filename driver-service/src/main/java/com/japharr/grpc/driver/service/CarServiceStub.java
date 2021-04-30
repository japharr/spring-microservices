package com.japharr.grpc.driver.service;

import com.japharr.grpc.driver.dto.CarDto;
import com.japharr.grpc.driver.utils.SecurityUtils;
import org.mvnsearch.rsocket.loadbalance.RSocketServiceRegistry;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarServiceStub {
    private final Mono<RSocketRequester> rSocketRequester;

    final MimeType BEARER_AUTHENTICATION_MIME_TYPE = new MediaType("message", "x.rsocket.authentication.bearer.v0");

    public CarServiceStub(RSocketRequester.Builder rsocketRequesterBuilder, ReactiveDiscoveryClient discoveryClient, RSocketServiceRegistry rsocketServiceRegistry) {
        this.rSocketRequester = Mono.just(rsocketServiceRegistry
            .buildLoadBalanceRSocket("car-service", rsocketRequesterBuilder));
    }

    public Mono<CarDto> getCarById (Long id) {
        return SecurityUtils.jwt().flatMap( token ->
            this.rSocketRequester.flatMap(req ->
                req.route("request-response")
                    .metadata(token.getTokenValue(), BEARER_AUTHENTICATION_MIME_TYPE)
                    .data(id).retrieveMono(CarDto.class)
            ));
    }

    public Flux<CarDto> getCarsByDriverId(Long id) {
        return Flux.just(new CarDto(1L, "Toyota"), new CarDto(2L, "Benz"));
    }
}
