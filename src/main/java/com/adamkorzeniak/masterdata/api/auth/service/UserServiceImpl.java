package com.adamkorzeniak.masterdata.api.auth.service;

import com.adamkorzeniak.masterdata.api.auth.repository.UserRepository;
import com.adamkorzeniak.masterdata.exception.exceptions.DuplicateUserException;
import com.adamkorzeniak.masterdata.api.auth.model.Role;
import com.adamkorzeniak.masterdata.api.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        validateUserNotAlreadyExists(user.getUsername());

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        if (newUser.getRole() == null) {
            newUser.setRole(Role.USER);
        }
        return userRepository.save(newUser);
    }

    @Override
    public User getUser(String username) {
        if (username == null) {
            throw new IllegalArgumentException();
        }
        return userRepository.findByUsername(username);
    }

    private void validateUserNotAlreadyExists(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new DuplicateUserException(username);
        }
    }

    @Override
    public User getUser(Principal user) {
        return getUser(user.getName());
    }

}
