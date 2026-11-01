package org.studyeasy.jrnlapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.studyeasy.jrnlapp.entity.User;
import org.studyeasy.jrnlapp.repository.UserRepository;
import org.studyeasy.jrnlapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//
//        userService.saveNewUser(user);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
@PutMapping("/{userName}")
public ResponseEntity<?> updateUser(
        @RequestBody User updatedUser,
        @PathVariable String userName) {

    User userInDb = userService.findByuserName(userName);

    if (userInDb == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    userService.updateUser(userInDb, updatedUser);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserById() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}