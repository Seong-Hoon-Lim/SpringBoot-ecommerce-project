package com.kittopmall;

import com.kittopmall.config.CustomPropertiesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(CustomPropertiesConfiguration.class)
@SpringBootApplication
public class SpringbootKittopmallProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootKittopmallProjectApplication.class, args);
    }

}
