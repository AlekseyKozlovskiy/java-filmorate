package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private Map<Long, User> userMap = new TreeMap<>();
    private InMemoryUserStorage inMemoryUserStorage;
    private UserService userService;

    public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    @GetMapping("/users")
    public ArrayList<User> getAll() {
        return inMemoryUserStorage.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new IncorrectParameterException(String.format("неверный id пользователя - %d", id));
        }
        return inMemoryUserStorage.getUser(id);
    }

    @GetMapping("/users/{id}/friends")
    public Set<User> getAllFriends(@PathVariable("id") Long id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<Long> getMutualFriends(@PathVariable("id") Long id,
                                      @PathVariable("otherId") Long otherId) {
        return userService.getMutualFriends(id, otherId);
    }


    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) throws ValidationException {
        return inMemoryUserStorage.createUser(user);
    }

    @PutMapping("/users")
    public User change(@RequestBody User user) throws ValidationException {
        return inMemoryUserStorage.changeUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Long id,
                          @PathVariable("friendId") Long friendId) {
        if (id < 1 || friendId < 1) {
            throw new IncorrectParameterException(String.format("неверный id пользователя - %d", id));
        }
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") Long id,
                             @PathVariable("friendId") Long friendId) {

        return userService.deleteFriend(id, friendId);
    }
}
