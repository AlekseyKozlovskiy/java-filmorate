package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> userMap = new TreeMap<>();

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
        if (validate(user)) {
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

    @Override
    public User change(User user) {
        if (validate(user)) {
            userMap.put(user.getId(), user);
            log.info("Данные изменены");
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }

    @Override
    public boolean validate(User user) {
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

    public Map<Long, User> getUserMap() {
        return userMap;
    }

    @Override
    public void delete(Long id) {
        userMap.remove(id);
    }
}

