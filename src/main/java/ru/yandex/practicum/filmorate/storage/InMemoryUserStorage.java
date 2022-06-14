package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.stereotype.Component;
import util.UserValidator;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@Component
@Slf4j

public class InMemoryUserStorage implements UserStorage {


    private final Map<Long, User> userMap = new TreeMap<>();

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<User>(userMap.values());
    }

    @Override
    public User get(Long id) {
        return userMap.get(id);
    }

    @Override
    public User create(User user) {
        if (UserValidator.validate(user)) {
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            log.info("Добавлена запись: " + user);
            userMap.put(user.getId(), user);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }


    public User change(User user) {
        if (UserValidator.validate(user)) {
            userMap.put(user.getId(), user);
            log.info("Данные изменены");
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }

    public Map<Long, User> getUserMap() {
        return userMap;
    }

    @Override
    public void delete(Long id) {
        userMap.remove(id);
    }


}

