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

    public CarServiceStub(RSocketRequester.Builder rsocketRequesterBuilder, ReactiveDiscoveryClient discoveryClient) {
        Mono<List<LoadbalanceTarget>> serversMono = discoveryClient
            .getInstances("CAR-SERVICE")
            .map(si -> {
                String tcp = si.getMetadata().getOrDefault("tcp", "5000");
//                TcpClient tcpClient = TcpClient.create()
//                    .host(si.getHost()).port(si.getPort());
                System.out.println("CarServiceStub: url: " + si.getUri().toString());
                System.out.println("CarServiceStub: path: " + si.getUri().getRawPath());
                System.out.println("CarServiceStub: port: " + si.getPort());
                System.out.println("CarServiceStub: tcp-port: " + tcp);
                HttpClient httpClient = HttpClient.create()
                    .host(si.getHost()).port(Integer.parseInt(tcp));
                return LoadbalanceTarget.from(si.getUri().toString(), WebsocketClientTransport.create(httpClient, "/rsocket"));
                //return LoadbalanceTarget.from(si.getUri().toString(), TcpClientTransport.create(tcpClient));
            })
            .collectList();

        this.rSocketRequester = Mono.just(rsocketRequesterBuilder
            //.setupRoute("/connect")
            //.setupData("test")
            .transports(serversMono.flux(), new RoundRobinLoadbalanceStrategy()));

//        this.rSocketRequester = rsocketRequesterBuilder
//            .rsocketConnector(connector -> connector
//                .reconnect(Retry.fixedDelay(Integer.MAX_VALUE, Duration.ofSeconds(1))))
//            .connectTcp("localhost", 7001).cache();
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
