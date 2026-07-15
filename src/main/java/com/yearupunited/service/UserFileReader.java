package com.yearupunited.service;

import com.yearupunited.model.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserFileReader {
    public ArrayList<User> readUserFile(String fileName) {
        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            bufferedReader.readLine();

            String line;
            while ((line =bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if  (parts.length != 2) {
                    System.out.println("Skipping incorrect line " + line);
                    continue;
                }
                users.add(new User(parts[0].trim(), parts[1].trim()));
            }
        }
        catch (IOException e){
            System.out.println("Error reading file " + fileName + ": " + e.getMessage());
        }
        return users;
    }
}
