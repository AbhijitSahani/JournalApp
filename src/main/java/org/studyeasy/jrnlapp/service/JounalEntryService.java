package org.studyeasy.jrnlapp.service;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.studyeasy.jrnlapp.entity.JournalEntry;
import org.studyeasy.jrnlapp.entity.User;
import org.studyeasy.jrnlapp.repository.JournalEntryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Component
public class JounalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userName) {

        try {
            User user = userService.findByuserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedOne = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedOne);
            userService.saveEntry(user);

        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }
    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntryRepository.save(journalEntry);
        } catch (Exception e)
        {
            log.error("Exception ", e);
        }
    }

    public List<JournalEntry> getAll() {

        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deletById(ObjectId id, String userName) {
        User user = userService.findByuserName(userName);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }


}
