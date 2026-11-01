package org.studyeasy.jrnlapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.studyeasy.jrnlapp.entity.User;
import org.studyeasy.jrnlapp.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create a new user
    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    // Save an existing user without encoding password again
    public void saveEntry(User user) {
        userRepository.save(user);
    }

    // Update user details
    public void updateUser(User userInDb, User updatedUser) {

        if (updatedUser.getUserName() != null
                && !updatedUser.getUserName().isBlank()) {
            userInDb.setUserName(updatedUser.getUserName());
        }

        if (updatedUser.getPassword() != null
                && !updatedUser.getPassword().isBlank()) {
            userInDb.setPassword(
                    passwordEncoder.encode(updatedUser.getPassword())
            );
        }

        userRepository.save(userInDb);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByuserName(String username) {
        return userRepository.findByuserName(username);
    }
}