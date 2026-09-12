package org.studyeasy.jrnlapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.studyeasy.jrnlapp.entity.JournalEntry;
import org.studyeasy.jrnlapp.entity.User;


//@Repository
//@Component
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUsername(String username);
}
