package com.example.manager.controller;

//Class was made so that we can Mock the System.Exit with a mock.method
//doReturn and that the test does not stop before the assert because System.exit also exits the unit test before completion.

public class ExitMenu {
    public boolean executeSystemExit () {
        System.exit(0);
        return true;
    }
}
