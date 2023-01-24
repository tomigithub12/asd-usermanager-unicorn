package com.example.manager.controller;

import com.example.manager.domain.User;
import com.example.manager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.mock.mockito.*;

//import org.junit.Test;
//import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;


class UserControllerTest {

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @InjectMocks
    UserController usercontrolla;
    public PasswordEncoder passwordEncoderMock = passwordEncoder();
    //private PasswordEncoder testPasswordEncoder = new BCryptPasswordEncoder();

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder testPasswordEncoder = new BCryptPasswordEncoder();

   @BeforeEach
   void setUp() throws Exception {
       MockitoAnnotations.openMocks(this);
       //MockitoAnnotations.initMocks(this);
   }

    @Test
    public void exampleTest() {
        // RemoteService has been injected into the reverser bean
        //given(this.remoteService.someCall()).willReturn("mock");
        //String reverse = reverser.reverseSomeCall();
        //assertThat(reverse).isEqualTo("kcom");
    }


    @Test
    void register_isSuccessful_returnUser() {
        User dummyUser = new User("benis","kebab","box", "asd123", 0, false, null);

        Mockito.when(repo.save(Mockito.any(User.class))).thenReturn(dummyUser);

        User test_user = usercontrolla.register(dummyUser.getFirstname(), dummyUser.getLastname(), dummyUser.getUsername(), dummyUser.getPassword());

        assertNotNull(test_user);

        assertEquals(dummyUser.getLastname(), test_user.getLastname());

        //UserController test_user_controller = new UserController();

        //User test_user = test_user_controller.register(dummyUser.getFirstname(), dummyUser.getLastname(), dummyUser.getUsername(), dummyUser.getPassword());

        //assertEquals(dummyUser, test_user);
    }

    @Test
    void checkIfUsernameTaken_isTaken_returnTrue() {
        Mockito.when(repo.existsById(anyString())).thenReturn(true);

        boolean response = usercontrolla.checkIfUsernameTaken("box");

        assertEquals(response, true);
    }

    @Test
    void checkIfUsernameTaken_isNotTaken_returnFalse() {
        Mockito.when(repo.existsById(anyString())).thenReturn(false);

        boolean response = usercontrolla.checkIfUsernameTaken("box");

        assertEquals(response, false);
    }


    @Test //--- not working ---
    void login_isSuccessful_returnTrue() {
        User dummyUser = new User("benis","kebab","box", "asd123", 0, true, null);
        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));
        Mockito.when(testPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean response = usercontrolla.login("box", "asd123");

        assertEquals(true, response);
    }


    @Test
    void loadTempUser_isSuccessful_returnUser() {
        User dummyUser = new User("benis","kebab","box", "asd123", 0, true, null);
        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));

        User testUser = usercontrolla.loadTempUser("box");

        assertEquals(dummyUser.getUsername(), testUser.getUsername());
    }

    @Test
    void loadTempUser_isNotSuccessful_returnNull() {
        Mockito.when(repo.findById(anyString())).thenReturn(null);

        User testUser = usercontrolla.loadTempUser("box");

        assertEquals(null, testUser);
    }

    @Test
    void changePassword() {
        User dummyUser = new User("benis","kebab","box", "asd123", 0, false, null);
        String dummyPasswordEncoded = "asdasdasdasd";
        Mockito.when(repo.save(Mockito.any(User.class))).thenReturn(dummyUser);
        //Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));
        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));
        //Mockito.when(testPasswordEncoder.encode(anyString())).thenReturn("asd1234");
        //tempUser.setPassword(passwordEncoder.encode(newPassword));
        Mockito.when(testPasswordEncoder.encode(anyString())).thenReturn(dummyPasswordEncoded);
        ///Mockito.when(Mockito.any(User.class).setPassword(anyString()));
        ///Mockito.when(testPasswordEncoder.encode(anyString())).thenReturn(dummyPasswordEncoded);
        //tempUser.setPassword(passwordEncoder.encode(newPassword));
        //Mockito.when(dummyUser.setPassword(testPasswordEncoder.encode(dummyUser.getPassword()))).thenReturn(dummyPasswordEncoded);
        //Mockito.when(testPasswordEncoder.matches("asd123", dummyPassword)).thenReturn(Boolean.valueOf("asd123"));
        User testUser = usercontrolla.changePassword("box", "asd12345");

        //assertEquals("asd1234", testUser.getPassword());
        assertEquals(testUser.getPassword(), testPasswordEncoder.matches("asd12345", testUser.getPassword()));

    }

    @Test
    void changePasswordTest() {


        //User tempUser = repo.findById(username).orElseThrow();

        User tempUser = new User("testFirstNameFirstInit","testLastNameFirstInit","usernameFirstInit", "1337FirstInit", 1, true, null);
        String newPassword = passwordEncoderMock.encode(tempUser.getPassword());
        tempUser.setPassword(newPassword);
        boolean machPassword = passwordEncoderMock.matches("1337FirstInit", tempUser.getPassword());
        assert machPassword : "Password does NOT match";
        //repo.save(tempUser);
        //return repo.save(tempUser);
    }
}