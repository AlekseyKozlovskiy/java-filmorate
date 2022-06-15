package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import model.User;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;


    public User create(User user) {
        return userStorage.create(user);
    }

    public void delete(Long id) {
        userStorage.delete(id);
    }

    public User change(User user) {
        return userStorage.change(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User get(Long id) {
        return userStorage.get(id);
    }

    public void addFriend(Long id, Long friendId) {
        friendStorage.add(id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) {
        friendStorage.delete(id, friendId);
    }

    public Set<User> getAllFriends(Long id) {
        return friendStorage.getAll(id);
    }

    public Set<User> getMutualFriends(Long id, Long friendId) {
        return friendStorage.getMutual(id, friendId);
    }
}
