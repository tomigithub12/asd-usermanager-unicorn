package com.example.manager;

import java.util.Date;
import java.util.Scanner;

import com.example.manager.controller.MenuController;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.manager.domain.User;
import com.example.manager.controller.UserController;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories
//@Configuration
public class AsdUserManagerApplication implements CommandLineRunner {

    @Autowired
    MenuController menuController = new MenuController();

    public static void main(String[] args) {
        SpringApplication.run(AsdUserManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        menuController.showMenu();
    }

}
