package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@Slf4j
public class UserController {
    private Map<Long, User> userMap = new TreeMap<>();

    @GetMapping("/users")
    public ArrayList<User> getAll() {
        return new ArrayList<User>(userMap.values());
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (validate(user)) {
            if (user.getName().isEmpty()){
                user.setName(user.getLogin());
            }
            log.info("Добавлена запись: " + user);
            userMap.put(user.getId(), user);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }

    @PutMapping("/users")
    public User change(@RequestBody User user) throws ValidationException {
        if (validate(user)) {
            userMap.put(user.getId(), user);
            log.info("Данные изменены");
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }

    public static boolean validate (User user){

        String validEmail = "(.+@.+\\..+)";
        Pattern p = Pattern.compile(validEmail);
        Matcher m = p.matcher(user.getEmail());

        return !(user.getEmail().isBlank())
                && !(user.getLogin() == null)
                && !(user.getLogin().isBlank())
                && !(user.getLogin().contains(" "))
                && !(user.getBirthday().isAfter(LocalDate.now()))
                && m.matches();
    }
}
