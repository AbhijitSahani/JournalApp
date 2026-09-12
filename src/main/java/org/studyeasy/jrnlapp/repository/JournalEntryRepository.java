package org.studyeasy.jrnlapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.studyeasy.jrnlapp.entity.JournalEntry;


//@Repository
//@Component
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
