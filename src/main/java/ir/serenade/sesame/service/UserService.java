package ir.serenade.sesame.service;

import ir.serenade.sesame.domain.Role;
import ir.serenade.sesame.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Role findRoleByName(String name);

    User findUserByUsername(String username);

    Role saveRole(Role role);

    User saveUser(User user);
}
