package com.cybernetics.user_managment_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UserManagmentMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagmentMsApplication.class, args);
    }

}
