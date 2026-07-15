package com.yearupunited;

import com.yearupunited.service.TransactionManager;
import com.yearupunited.service.UserManager;
import com.yearupunited.ui.UserInterface;

public class Main {
    public static void main(String[] args) {

        TransactionManager manager = new TransactionManager();
        UserManager userManager = new UserManager();
        UserInterface ui = new UserInterface(manager, userManager);
        // Starts Here
        ui.start();

    }
}
