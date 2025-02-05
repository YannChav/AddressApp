package org.mines.address.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.mines.address"})
public class AddressApp
{
    public static void main(String[] args) {
        SpringApplication.run(AddressApp.class, args);
    }
}
