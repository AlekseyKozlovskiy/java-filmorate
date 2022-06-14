package ru.yandex.practicum.filmorate.storage;

import model.User;

import java.util.Set;

public interface FriendStorage {
    void addFriend(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId);

    Set<User> getAllFriends(Long id);

    Set<User> getMutualFriends(Long id, Long friendId);
}
