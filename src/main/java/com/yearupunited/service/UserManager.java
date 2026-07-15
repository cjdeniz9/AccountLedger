package com.yearupunited.service;

import com.yearupunited.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserManager {
    private final List<User> users;
    private final String fileName;
    private User currentUser;   //needs a login to not be null

    public UserManager() {
        this.fileName = "src/main/users.csv";
        this.users = new ArrayList<>(new UserFileReader().readUserFile(fileName));
        this.currentUser = null;
    }

    public Optional<User> findById(String id) {
        return users.stream()
                .filter(u -> u.getUserId().equalsIgnoreCase(id))
                .findFirst();
    }
    public boolean exists(String id) {
        return findById(id).isPresent();
    }

    public boolean login(String id) {
        Optional<User> match = findById(id);
        if (match.isPresent()) {
            currentUser = match.get();
            return true;
        }
        return false;
    }

    public Optional<User> createUser(String id, String userName) {
        if (exists(id)) return Optional.empty();

        User user = new User(id, userName);
        users.add(user);
        UserFileWriter.writeUserFile(fileName, user);
        return Optional.of(user);
    }
    public User getCurrentUser() {
        return currentUser;
    }
}
