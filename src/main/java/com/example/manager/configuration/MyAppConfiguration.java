package com.example.manager.configuration;

import com.example.manager.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableMongoRepositories
public class MyAppConfiguration {

    //@Autowired
    //@Bean
    //public UserController uc() { return new UserController(); }

    //@Bean
    //public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
