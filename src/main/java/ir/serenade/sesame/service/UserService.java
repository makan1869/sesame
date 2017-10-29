package ir.serenade.sesame.service;

import ir.serenade.sesame.domain.entity.Device;
import ir.serenade.sesame.domain.entity.Role;
import ir.serenade.sesame.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Role findRoleByName(String name);

    User findUserByUsername(String username);

    Role saveRole(Role role);

    User saveUser(User user);

    Device saveDevice(Device device);

    Device findDeviceByUserAndUuid(User user, String deviceId);
}
