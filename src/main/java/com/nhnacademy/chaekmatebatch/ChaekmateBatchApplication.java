package com.nhnacademy.chaekmatebatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChaekmateBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChaekmateBatchApplication.class, args);
    }

}
