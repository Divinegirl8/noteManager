package africa.note.manager.data.repository;

import africa.note.manager.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findUserByUserId(String userId);
    User findUserByUsername(String name);
}
