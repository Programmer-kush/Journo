package Project.SpringBoot.JournalApp.repository;

import Project.SpringBoot.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User,ObjectId> {

        User findByUserName(String userName);

        void deleteByUserName(String userName);
}
