package com.example.manager.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.example.manager.configuration.MyAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.example.manager.repository.UserRepository;

import com.example.manager.domain.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.manager.repository.UserRepository;

@Service
//@Controller
//@Configuration
//@ComponentScan(basePackages = "com.example.manager")
//@ComponentScan(basePackageClasses = MyAppConfiguration.class)
//@EnableMongoRepositories
public class UserController {

    public static final int maxFailedAttempts = 3;

    // 60 Seconds
    private static final long lockTimeDuration = 60 * 1000;

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @Autowired
    private UserRepository repo;




    //private UserRepository userRepository() { this.userRepository(); };




    //@Autowired
    //private PasswordEncoder passwordEncoder;

    final private PasswordEncoder passwordEncoder = passwordEncoder();

    //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    //final private PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

    public UserController() {
        super();

        //userController = userController;
    }


    public User register(String firstname, String lastname, String username, String password) {
        String bCryptEncodedPassword = passwordEncoder.encode(password);

        int failedAttempt = 0;
        boolean accountNonLocked = true;
        Date lockTime = null;

        User user = new User(firstname, lastname, username, bCryptEncodedPassword, failedAttempt, accountNonLocked, lockTime);
        return repo.save(user);
        //return userRepository().save(user);
    }

    public boolean checkIfUsernameTaken(String username) {
        return repo.existsById(username);
    }


    public boolean login(String username, String password) {
        //User tempUser = null;
        //try {
        //    tempUser = repo.findById(username).get();
        //} catch (Exception e) {

        //}

        User tempUser = loadTempUser(username);

        if (tempUser != null) {
            if (passwordEncoder.matches(password, tempUser.getPassword()) && tempUser.getAccountNonLocked()) {
                resetFailedAttempts(username);
                return true;
            } else {
                unlockAfterTimeExpired(username);
                //tempUser = repo.findById(username).get();
                if (tempUser.getAccountNonLocked() && tempUser.getFailedAttempt() < maxFailedAttempts) {
                    increaseFailedAttempts(username);
                    //tempUser = repo.findById(username).get();
                    System.out.println("Failed attempt " + tempUser.getFailedAttempt() + "/4.");
                } else {
                    //tempUser = repo.findById(username).get();
                    if (tempUser.getFailedAttempt() == maxFailedAttempts) {
                        lock(username);
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        LocalDateTime  loginFailed = LocalDateTime.now().plusSeconds(60);
                        System.out.println("User locked until " + dtf.format(loginFailed) + ".\n");
                        return false;
                    }
                    unlockAfterTimeExpired(username);
                }
                return false;
            }
        }
        System.out.println("Username does not match an existing one. \n Please enter a valid username\n");
        return false;
    }

    public User loadTempUser (String username) {
        User tempUser = null;
        try {
            tempUser = repo.findById(username).orElseThrow();

        } catch (Exception e) {
            System.out.println("Username not found!");
            return null;
        }
        return tempUser;
    }

    public User changePassword(String username, String newPassword) {
        User tempUser = repo.findById(username).orElseThrow();
        tempUser.setPassword(passwordEncoder.encode(newPassword));
        //repo.save(tempUser);
        return repo.save(tempUser);
    }

    public void logout(String username, String password) {
        username = null;
        password = null;
        System.out.println("You are logged out!\n");
    }

    public void increaseFailedAttempts(String username) {
        User tempUser = repo.findById(username).orElseThrow();
        int failedAttempttempt = tempUser.getFailedAttempt() + 1;
        tempUser.setFailedAttempt(failedAttempttempt);
        repo.save(tempUser);
    }

    public void resetFailedAttempts(String username) {
        User tempUser = repo.findById(username).orElseThrow();
        tempUser.setFailedAttempt(0);
        repo.save(tempUser);
    }

    private void lock(String username) {
        User tempUser = repo.findById(username).orElseThrow();
        tempUser.setAccountNonLocked(false);
        tempUser.setLockTime(new Date());
        repo.save(tempUser);
    }

    public boolean unlockAfterTimeExpired(String username) {
        User tempUser = repo.findById(username).orElseThrow();
        if (tempUser.getLockTime() != null) {
            long lockTime = tempUser.getLockTime().getTime();
            long currentTime = System.currentTimeMillis();

            if (lockTime + lockTimeDuration < currentTime) {
                tempUser.setFailedAttempt(0);
                tempUser.setAccountNonLocked(true);
                tempUser.setLockTime(null);

                repo.save(tempUser);

                return true;
            }

            return false;
        }

        return false;
    }

    public void deleteAccount(String username) {
        repo.deleteById(username);
    }


}
