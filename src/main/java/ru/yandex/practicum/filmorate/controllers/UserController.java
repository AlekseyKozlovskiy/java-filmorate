package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import model.User;
import model.UserUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Map;
import java.util.TreeMap;


@RestController
@Slf4j
public class UserController {
    private Map<Long, User> userMap = new TreeMap<>();

    @GetMapping("/users")
    public Map<Long, User> findAll() {
        return userMap;
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (UserUtils.chek(user)) {
            if (user.getName().isEmpty()){
                user.setName(user.getLogin());
            }
            log.info("Добавлена запись: " + user);
            userMap.put(1L, user);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }

    @PutMapping("/users")
    public User change(@RequestBody User user) throws ValidationException {
        if (UserUtils.chek(user)) {
            userMap.put(1L, user);
            log.info("Данные изменены");
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }
}
