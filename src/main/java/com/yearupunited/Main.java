package com.yearupunited;

import com.yearupunited.service.TransactionManager;
import com.yearupunited.ui.UserInterface;

public class Main {
    public static void main(String[] args) {

        TransactionManager manager = new TransactionManager();
        UserInterface ui = new UserInterface(manager);
        ui.homeScreen();

    }
}