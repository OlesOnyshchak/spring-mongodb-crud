package dk.sparnord.mongodb.repository;

import dk.sparnord.mongodb.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);

    void deleteById(String id);
}
