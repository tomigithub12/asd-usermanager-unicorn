package com.example.manager.controller;

import com.example.manager.domain.User;
import com.example.manager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuControllerTest {

    @InjectMocks
    MenuController menucontrolla;

    @Mock
    Scanner mockScn = mock(Scanner.class);

    @Mock
    UserController usercontrolla;
    public User userMock = new User("benis","kebab","box", "asd123", 0, false, null);;

    @Mock
    private UserRepository repo;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showMenu_test_exit() {

        MenuController menuControllerNotMocked = new MenuController();
        ExitMenu exitMenuMock = mock(ExitMenu.class);

        String input = "3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        when(exitMenuMock.executeSystemExit()).thenReturn(true);
        menuControllerNotMocked.exitMenu = exitMenuMock;

        menuControllerNotMocked.showMenu();

        assertEquals(true, exitMenuMock.executeSystemExit());
    }

    @Test
    void showMenu() {

        MenuController spyMenuControlla = spy(menucontrolla);

        MenuController mockMenu = mock(MenuController.class);
        Mockito.when(mockScn.nextInt()).thenReturn(1);

        doNothing().when(spyMenuControlla).showMenu();

        spyMenuControlla.showMenu();

        verify(spyMenuControlla,atLeast(1)).showMenu();

        /*String test_string = "1";

        Scanner mockScn = mock(Scanner.class);
        MenuController mockMenu = mock(MenuController.class);


        String input = "3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menucontrolla.showMenu();

        verify(mockMenu,atLeast(1)).showMenu();*/

    }

    @Test
    void registerMenu() {

        User dummyUser = new User("benis","kebab","box", "asd123", 0, false, null);

        MenuController spyMenuControlla = spy(menucontrolla);

        Mockito.when(repo.save(Mockito.any(User.class))).thenReturn(dummyUser);
        Mockito.when(mockScn.next()).thenReturn("josef");
        Mockito.when(mockScn.nextInt()).thenReturn(3);
        Mockito.when(repo.existsById(anyString())).thenReturn(false);

        doNothing().when(spyMenuControlla).showMenu();




        spyMenuControlla.registerMenu();

        verify(spyMenuControlla, atLeast(1)).showMenu();
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
*/

}

