package com.japharr.grpc.driver.config;

import org.mvnsearch.rsocket.loadbalance.RSocketServiceRegistry;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class RSocketConfig {
//    @Bean
//    public RSocketStrategies rSocketStrategies() {
//        return RSocketStrategies.builder()
//            .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
//            .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
//            .build();
//    }

//    @Bean
//    public RSocketRequester carServiceRequester(
//        RSocketRequester.Builder builder, RSocketServiceRegistry rsocketServiceRegistry) {
//        return rsocketServiceRegistry.buildLoadBalanceRSocket("CAR-SERVICE", builder);
//    }
}
