package ir.serenade.sesame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SesameApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SesameApplication.class, args);
        System.out.println("Server up and running");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<SesameApplication> applicationClass = SesameApplication.class;
}
