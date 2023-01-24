package com.example.manager.controller;

import com.example.manager.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.xmlunit.builder.Input;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuControllerTest {

    @InjectMocks
    MenuController menucontrolla;

    @Mock
    UserController usercontrolla;
    public User userMock = new User("benis","kebab","box", "asd123", 0, false, null);;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        //MockitoAnnotations.initMocks(this);
    }

/*
    @Test
    void showMenu_test_register() {

        Scanner mockScn = mock(Scanner.class);
        MenuController mockMenu = mock(MenuController.class);

        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menucontrolla.showMenu();

        verify(mockMenu,atLeast(1)).showMenu();

        //TODO: username und pw input damit es beendet
    }
    @Test
    void showMenu_test_login() {

        Scanner mockScn = mock(Scanner.class);
        MenuController mockMenu = mock(MenuController.class);

        String input = "2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menucontrolla.showMenu();

        verify(mockMenu,atLeast(1)).showMenu();

        //TODO: username und pw input damit es beendet
    }


    @Test
    void showMenu_test_exit() {

        MenuController mockMenu = mock(MenuController.class);

        String input = "3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menucontrolla.showMenu();

        verify(mockMenu,atLeast(1)).showMenu();

    }

 */
}

