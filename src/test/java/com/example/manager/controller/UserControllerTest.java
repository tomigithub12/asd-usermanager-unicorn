package com.example.manager.controller;

import com.example.manager.domain.User;
import com.example.manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
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

import static org.assertj.core.api.Assertions.assertThat;



class UserControllerTest {

    //@Mock
    //@Autowired
    //private UserRepository repo;

    @Test
    public void exampleTest() {
        // RemoteService has been injected into the reverser bean
        //given(this.remoteService.someCall()).willReturn("mock");
        //String reverse = reverser.reverseSomeCall();
        //assertThat(reverse).isEqualTo("kcom");
    }


    @Test
    void register() {
        User dummyUser = new User("benis","kebab","box", "asd123", 0, false, null);

        //Mockito.when(repo.save(Mockito.any(User.class))).thenAnswer(dummyUser);


        UserController test_user_controller = new UserController();

        User test_user = test_user_controller.register(dummyUser.getFirstname(), dummyUser.getLastname(), dummyUser.getUsername(), dummyUser.getPassword());

        assertEquals(dummyUser, test_user);
    }
}