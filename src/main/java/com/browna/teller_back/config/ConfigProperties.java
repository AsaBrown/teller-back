package com.browna.teller_back.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("config")
public record ConfigProperties(String jwtSigningKey ) {

}
