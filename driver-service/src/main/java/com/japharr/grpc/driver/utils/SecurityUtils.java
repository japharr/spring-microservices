package com.japharr.grpc.driver.utils;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

public abstract class SecurityUtils {
    private static final String USER_LOGIN_CLAIM_NAME = "preferred_username";

    public static Mono<Jwt> jwt() {
        return ReactiveSecurityContextHolder.getContext()
            .map(context -> context.getAuthentication().getPrincipal()).cast(Jwt.class);
    }

    public static Mono<String> currentUserLogin(){
        return jwt().map ( jwt ->
            jwt.getClaimAsString(USER_LOGIN_CLAIM_NAME)
        );
    }
}
