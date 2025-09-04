package Project.SpringBoot.JournalApp.service;

import Project.SpringBoot.JournalApp.entity.User;
import Project.SpringBoot.JournalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@Slf4j // ye lombok wala logger ka instance bana dega
public class UserService {

    @Autowired
    private UserRepository userRepository; //dependency injection

    //slf4j annotation did it
    //private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public boolean saveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            log.info("kataaaa");
            return false;
        }


    }


    public void saveUser(User user) {
        userRepository.save(user);
    }


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        userRepository.save(user);

    }
}
