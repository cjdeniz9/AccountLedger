package com.yearupunited.service;

import com.yearupunited.model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserFileWriter {
    public static void writeUserFile(String fileName, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.newLine();
            writer.write(user.getUserId() + "|" + user.getUserName());
        }
        catch (IOException e ) {
            System.out.println("Error writing to file" + e.getMessage());
        }
    }
}
