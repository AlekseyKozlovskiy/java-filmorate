package ru.yandex.practicum.filmorate.storage;

import model.User;

import java.util.List;

public interface UserStorage {
    List<User> getAllUsers();
    User get(Long id);
    User create(User user);
    User change(User user);
    boolean validate(User user);
    void delete(Long id);
}
