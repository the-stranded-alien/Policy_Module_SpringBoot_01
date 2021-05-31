package springboot.policy_module.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import springboot.policy_module.controllers.dto.UserRegistrationDto;
import springboot.policy_module.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    User findByUsername(String username);
    User save(UserRegistrationDto registration);
}
