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


import java.util.*;

@RestController
@RequestMapping("/journal")
public class JounralEntryControllerV2 {


    @Autowired
    private JounalEntryService jounalEntryService ;

    @Autowired
    private UserService userService;


    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByuserName(userName);
        List<JournalEntry> all = jounalEntryService.getAll();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all ,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName) {
        try{
           // User user = userService.findByuserName(userName);
            jounalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {

        Optional<JournalEntry> journalEntry = jounalEntryService.findById(myId);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId,@PathVariable String userName) {

        jounalEntryService.deletById(myId,userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName) {

        JournalEntry oldEntry = jounalEntryService.findById(myId).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals("") ? newEntry.getTitle():oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("") ? newEntry.getContent():oldEntry.getContent());
            jounalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
