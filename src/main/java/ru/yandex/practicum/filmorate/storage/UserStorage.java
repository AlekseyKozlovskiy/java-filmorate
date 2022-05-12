package ru.yandex.practicum.filmorate.storage;

import model.User;

import java.util.ArrayList;

public interface UserStorage {
    ArrayList<User> getAllUsers();
    User getUser(Long id);
    User createUser(User user);
    User changeUser(User user);
    boolean validate(User user);
}
