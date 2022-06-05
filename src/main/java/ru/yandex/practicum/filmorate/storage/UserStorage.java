package ru.yandex.practicum.filmorate.storage;

import model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    User create(User user);

    void delete(Long id);

    User change(User user);

    List<User> getAllUsers();

    User get(Long id);

    void addFriend(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId);

    Set<User> getAllFriends(Long id);

    Set<User> getMutualFriends(Long id, Long friendId);


}
