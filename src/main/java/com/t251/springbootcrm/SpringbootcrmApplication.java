package com.t251.springbootcrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author world
 */
@SpringBootApplication
@EnableScheduling
public class SpringbootcrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootcrmApplication.class, args);
    }

}
