package com.example.manager.controller;

import com.example.manager.domain.User;
import com.example.manager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;


class UserControllerTest {

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @InjectMocks
    UserController usercontrolla;
    public User userMock = new User("benis","kebab","box", "asd123", 0, false, null);;

    @Mock
    private UserRepository repo;
    public PasswordEncoder passwordEncoderMock = passwordEncoder();


    @Mock
    private PasswordEncoder testPasswordEncoder = new BCryptPasswordEncoder();

   @BeforeEach
   void setUp() throws Exception {
       MockitoAnnotations.openMocks(this);
   }


    @Test
    void register_isSuccessful_returnUser() {
        User dummyUser = new User("benis","kebab","box", "asd123", 0, false, null);

        Mockito.when(repo.save(Mockito.any(User.class))).thenReturn(dummyUser);

        User test_user = usercontrolla.register(dummyUser.getFirstname(), dummyUser.getLastname(), dummyUser.getUsername(), dummyUser.getPassword());

        assertNotNull(test_user);

        assertEquals(dummyUser.getLastname(), test_user.getLastname());

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


    @Test
    void login_isSuccessful_returnTrue_V2() {
        User dummyUser = new User("benis","kebab","box", "$2a$10$2x6AfON5G4KS2m6RhPoKq.Yc8iaDFMKK8wGq5YoKWfEdZNPCk9CJ.", 0, true, null);
        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));
        Mockito.when(testPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean response = usercontrolla.login("box", "asd12345");

        assertEquals(true, response);
    }


    @Test
    void login_FailedAttempt_returnFalse() {
        Date newDate = new Date(2022, Calendar.JANUARY, 3, 2, 44, 26);
        User dummyUser = new User("benis","kebab","box", "asd123", 0, true, newDate);
        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));
        Mockito.when(testPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean response = usercontrolla.login("box", "asd123");

        assertEquals(false, response);
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
        User dummyUserEncryptedPW = new User("benis","kebab","box", "asdasdasdasd", 0, false, null);

        String dummyPasswordEncoded = "asdasdasdasd";
        Mockito.when(repo.save(Mockito.any(User.class))).thenReturn(dummyUserEncryptedPW);
        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));

        User testUser = usercontrolla.changePassword("box", "asd12345");

        assertEquals(testUser.getPassword(), dummyPasswordEncoded);
    }

    @Test
    void changePasswordMethodWithoutMocksAndRealEncryption() {
        User dummyUser = new User("benis","kebab","box", "asd123", 0, false, null);
        User dummyUserEncryptedPW = new User("benis","kebab","box", "asdasdasdasd", 0, false, null);

        String dummyPasswordEncoded = "asdasdasdasd";
        Mockito.when(repo.save(Mockito.any(User.class))).thenReturn(dummyUser);
        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));
        User testUser = usercontrolla.changePassword("box", "asd12345");
        assertEquals(dummyUser.getPassword(), testUser.getPassword());
    }

    @Test
    void changePasswordTest() {

        User tempUser = new User("testFirstNameFirstInit","testLastNameFirstInit","usernameFirstInit", "1337FirstInit", 1, true, null);
        String newPassword = passwordEncoderMock.encode(tempUser.getPassword());
        tempUser.setPassword(newPassword);
        boolean machPassword = passwordEncoderMock.matches("1337FirstInit", tempUser.getPassword());
        assert machPassword : "Password does NOT match";
    }


    @Test
    void login_isSuccessful_returnTrue_LockedWorking() {
        Date newDate = new Date(2022, Calendar.JANUARY, 3, 2, 44, 26);
        User dummyUser = new User("benis","kebab","box", "asd123", 3, true, newDate);
        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));
        Mockito.when(testPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean response = usercontrolla.login("box", "asd123");

        assertEquals(false, response);
    }

    @Test
    void unlockAfterTimeExpiredTest() {
        Date newDate = new Date(2022, Calendar.JANUARY, 3, 2, 44, 26);

        User dummyUser = new User("benis","kebab","box", "asd123", 3, true, newDate);

        long newDate2 = System.currentTimeMillis()-70000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(newDate2);
        dummyUser.setLockTime(cal.getTime());

        Mockito.when(repo.findById(anyString())).thenReturn(Optional.of(dummyUser));

        boolean response = usercontrolla.login("box", "asd123");

        assertEquals(false, response);
    }

}