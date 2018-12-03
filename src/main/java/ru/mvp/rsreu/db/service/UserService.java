package ru.mvp.rsreu.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mvp.rsreu.db.entity.User;
import ru.mvp.rsreu.db.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @PostConstruct //TODO Регистрация только для теста!
    public void registerUser() {
        User user = userRepository.findByUsername("q");
        if (user == null) {
            user = new User();
            user.setPassword(passwordEncoder.encode("q"));
            user.setUsername("q");
            user.setActive(true);
            user.setRoles(Collections.singleton(User.Role.ADMIN));
            userRepository.save(user);
        }
    }
}