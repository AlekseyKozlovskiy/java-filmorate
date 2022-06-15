package ru.yandex.practicum.filmorate.storage;

import model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    void delete(Long id);

    User change(User user);

    List<User> getAllUsers();

    User get(Long id);


}
