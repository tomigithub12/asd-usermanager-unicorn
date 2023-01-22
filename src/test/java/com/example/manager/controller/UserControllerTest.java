package com.example.manager.controller;

import com.example.manager.domain.User;
import com.example.manager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.ArgumentMatchers.anyString;


class UserControllerTest {

    @InjectMocks
    UserController usercontrolla;

    @Mock
    private UserRepository repo;

   @BeforeEach
   void setUp() throws Exception {
       MockitoAnnotations.initMocks(this);
   }

    @Test
    public void exampleTest() {
        // RemoteService has been injected into the reverser bean
        //given(this.remoteService.someCall()).willReturn("mock");
        //String reverse = reverser.reverseSomeCall();
        //assertThat(reverse).isEqualTo("kcom");
    }


    @Test
    void test_register() {
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
    void checkIfUsernameTaken() {
        Mockito.when(repo.existsById(anyString())).thenReturn(true);

        boolean response = usercontrolla.checkIfUsernameTaken("box");

        assertEquals(response, true);
    }
}