package com.hautrieu.chat.services;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.domains.User;

public class UserService {

    private final DataStorage storage;

    public UserService(DataStorage storage) {
        this.storage = storage;
    }

    public boolean login(String username, String password) {
        User attemptedUser = storage.getUsers().getFirst(user -> user.getUsername().equals(username));
        if (attemptedUser == null) {
            return false;
        }
        return attemptedUser.login(password);
    }


    public boolean addUser(String username, String password) {
        User existing = storage.getUsers().getFirst(user -> user.getUsername().equals(username));
        if (existing != null) {
            return false;
        }

        User newUser = new User(username, password);
       storage.getUsers().add(newUser);
       return true;
    }
}