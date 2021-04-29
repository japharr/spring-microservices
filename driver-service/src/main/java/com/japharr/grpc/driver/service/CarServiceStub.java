package com.japharr.grpc.driver.service;

import com.japharr.grpc.driver.dto.CarDto;
import com.japharr.grpc.driver.dto.DriverDto;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.loadbalance.RoundRobinLoadbalanceStrategy;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mvnsearch.rsocket.loadbalance.RSocketServiceRegistry;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Service
public class CarServiceStub {
    private final Mono<RSocketRequester> rSocketRequester;

    public CarServiceStub(RSocketRequester.Builder rsocketRequesterBuilder, ReactiveDiscoveryClient discoveryClient, RSocketServiceRegistry rsocketServiceRegistry) {
        this.rSocketRequester = Mono.just(rsocketServiceRegistry
            .buildLoadBalanceRSocket("car-service", rsocketRequesterBuilder));
    }

    public Mono<CarDto> getCarById (Long id) {
        return this.rSocketRequester.flatMap(req ->
            req.route("request-response")
                .data(id).retrieveMono(CarDto.class));
        //return Mono.just(new CarDto(1L, "Toyota"));
    }

    public Flux<CarDto> getCarsByDriverId(Long id) {
        return Flux.just(new CarDto(1L, "Toyota"), new CarDto(2L, "Benz"));
    }
}
