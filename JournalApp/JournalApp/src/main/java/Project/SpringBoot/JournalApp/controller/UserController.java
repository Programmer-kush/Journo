package Project.SpringBoot.JournalApp.controller;

import Project.SpringBoot.JournalApp.entity.User;
import Project.SpringBoot.JournalApp.repository.UserRepository;
import Project.SpringBoot.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user") //ab iss class ke endpoint isse se shuru hoge
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;




    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUsername(userName);


                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userService.saveNewUser(userInDb);


            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
