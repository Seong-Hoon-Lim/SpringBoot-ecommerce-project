package com.kittopmall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class CustomPropertiesConfiguration {

    @Getter @Setter private String secret;

    @Getter @Setter private long tokenValidityInMilliseconds;

}
