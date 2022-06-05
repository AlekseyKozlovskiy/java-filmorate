package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new IncorrectParameterException(String.format("неверный id пользователя - %d", id));
        }
        return userService.get(id);
    }

    @PutMapping("/users")
    public User change(@RequestBody User user) {
        return userService.change(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new IncorrectParameterException(String.format("неверный id пользователя - %d", id));
        }
        userService.delete(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void friendRequest(@PathVariable("id") Long id,
                              @PathVariable("friendId") Long friendId) {
        if (id < 1 || friendId < 1) {
            throw new IncorrectParameterException(String.format("неверный id пользователя - %d", id));
        }
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Long id,
                             @PathVariable("friendId") Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Set<User> getAllFriends(@PathVariable("id") Long id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> getMutualFriends(@PathVariable("id") Long id,
                                      @PathVariable("otherId") Long otherId) {
        return userService.getMutualFriends(id, otherId);
    }
}
