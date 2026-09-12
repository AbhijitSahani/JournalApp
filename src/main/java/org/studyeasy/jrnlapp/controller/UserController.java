package org.studyeasy.jrnlapp.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studyeasy.jrnlapp.entity.JournalEntry;
import org.studyeasy.jrnlapp.entity.User;
import org.studyeasy.jrnlapp.service.JounalEntryService;
import org.studyeasy.jrnlapp.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class UserController {


    @Autowired
    private UserService userService ;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.saveEntry(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User userInDb=userService.findByUsername(user.getUserName());
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
