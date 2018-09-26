package dk.sparnord.mongodb.resource;

import dk.sparnord.mongodb.entity.User;
import dk.sparnord.mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/user")
public class UserResource {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoOperations mongoOperations;

    @GetMapping("{name}")
    @ResponseBody
    public User findUserByID(@PathVariable("name") String name) {
        return userRepository.findByName(name);
    }

    @GetMapping
    @ResponseBody
    public List<User> findUserByID() {
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseBody
    public User saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("{name}")
    public void deleteUser(@PathVariable("name") String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        mongoOperations.findAndRemove(query, User.class);
    }

    @PutMapping
    @ResponseBody
    public User updateUser(@RequestBody User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(user.getName()));

        Update update = new Update();
        update.set("age", user.getAge());
        update.set("address.street", user.getAddress().getStreet());
        update.set("address.city", user.getAddress().getCity());

        return mongoOperations.findAndModify(query, update, User.class);
    }
}
