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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JounralEntryControllerV2 {

    @Autowired
    private JounalEntryService jounalEntryService;

    @Autowired
    private UserService userService;

    private String currentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        User user = userService.findByuserName(currentUserName());
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            jounalEntryService.saveEntry(myEntry, currentUserName());
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
        User user = userService.findByuserName(currentUserName());
        boolean owned = user.getJournalEntries().stream()
                .anyMatch(x -> x.getId().equals(myId));
        if (owned) {
            Optional<JournalEntry> entry = jounalEntryService.findById(myId);
            if (entry.isPresent()) {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId) {
        boolean removed = jounalEntryService.deletById(myId, currentUserName());
        return removed
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId myId,
                                             @RequestBody JournalEntry newEntry) {
        User user = userService.findByuserName(currentUserName());
        boolean owned = user.getJournalEntries().stream()
                .anyMatch(x -> x.getId().equals(myId));
        if (owned) {
            JournalEntry old = jounalEntryService.findById(myId).orElse(null);
            if (old != null) {
                if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                    old.setTitle(newEntry.getTitle());
                }
                if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                    old.setContent(newEntry.getContent());
                }
                jounalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}