package ru.yandex.practicum.filmorate.storage;

import model.User;

import java.util.Set;

public interface FriendStorage {
    void add(Long id, Long friendId);

    void delete(Long id, Long friendId);

    Set<User> getAll(Long id);

    Set<User> getMutual(Long id, Long friendId);
}
