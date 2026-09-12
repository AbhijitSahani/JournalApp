package org.studyeasy.jrnlapp.service;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.studyeasy.jrnlapp.entity.JournalEntry;
import org.studyeasy.jrnlapp.repository.JournalEntryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Component
public class JounalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository ;


    public void saveEntry(JournalEntry journalEntry) {

        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);

        }
        catch(Exception e){

            log.error("Exception ", e );

        }


    }

    public List<JournalEntry> getAll() {

        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deletById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }


}
