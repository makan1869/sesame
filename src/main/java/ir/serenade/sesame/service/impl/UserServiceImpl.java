package ir.serenade.sesame.service.impl;

import ir.serenade.sesame.domain.entity.Device;
import ir.serenade.sesame.domain.entity.Role;
import ir.serenade.sesame.domain.entity.User;
import ir.serenade.sesame.repository.DeviceRepository;
import ir.serenade.sesame.repository.RoleRepository;
import ir.serenade.sesame.repository.UserRepository;
import ir.serenade.sesame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User with username = " + s + " Not Found");
        }
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }


    @Override
    public User saveUser(User user) {
        if(user.isPasswordDirty()) {
            user._setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device findDeviceByUserAndUuid(User user, String deviceId) {
        return deviceRepository.findDeviceByUserAndUuid(user, deviceId);
    }
}
