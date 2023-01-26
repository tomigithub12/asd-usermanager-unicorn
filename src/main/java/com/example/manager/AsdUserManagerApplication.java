package com.example.manager;

import com.example.manager.controller.MenuController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories
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
