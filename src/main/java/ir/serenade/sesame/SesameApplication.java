package ir.serenade.sesame;

import ir.serenade.sesame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SesameApplication {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SesameApplication.class, args);
        System.out.println("Server up and running");
    }
}
