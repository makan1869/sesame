package ir.serenade.sesame.config;

import ir.serenade.sesame.domain.entity.Role;
import ir.serenade.sesame.domain.entity.User;
import ir.serenade.sesame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SesameStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Role adminRole = userService.findRoleByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = userService.saveRole(new Role("ROLE_ADMIN"));
        }

        Role userRole = userService.findRoleByName("ROLE_USER");
        if (userRole == null) {
            userRole = userService.saveRole(new Role("ROLE_USER"));
        }

        if (userService.findUserByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("password");
            admin.addRole(adminRole);
            userService.saveUser(admin);
        }
    }
}
