package org.studyeasy.jrnlapp.service;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.studyeasy.jrnlapp.entity.JournalEntry;
import org.studyeasy.jrnlapp.entity.User;
import org.studyeasy.jrnlapp.repository.JournalEntryRepository;
import org.studyeasy.jrnlapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository ;


    public void saveEntry(User user) {
         userRepository.save(user);

    }
    public List<User> getAll() {

        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deletById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
