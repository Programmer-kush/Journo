package Project.SpringBoot.JournalApp.service;

import Project.SpringBoot.JournalApp.entity.User;
import Project.SpringBoot.JournalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

//@SpringBootTest  ye poori application ko load kar deta hai to time jyada lega testing mai unecessary
public class UserDetailServiceImplTests {

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Mock   //@Moclkitobean is used when spring ka poora context load ho only uss dependency ko mock ke liye use karte hai
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this); // isme jitni bhi mocks dependency hai unhe initialize kardo aur inject bhi kardo
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(anyString())).thenReturn(User.builder().userName("kush").password("jdsoj").roles(new ArrayList<>()).build());
        UserDetails User=userDetailService.loadUserByUsername("kush");
        Assertions.assertNotNull(User);
    }
}
