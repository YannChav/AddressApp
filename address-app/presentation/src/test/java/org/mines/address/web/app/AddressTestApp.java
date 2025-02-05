package org.mines.address.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.mines.address"})
public class AddressTestApp
{
    public static void main(String[] args) {
        SpringApplication.run(AddressTestApp.class, args);
    }
}
