package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import model.NumberGenerator;
import model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import util.UserValidator;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        if (!UserValidator.validate(user)) {
            throw new ValidationException("ошибка");
        }
        user.setId(NumberGenerator.getUserId());
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        jdbcTemplate.update("INSERT INTO FILMS.USERS (ID, NAME, BIRTHDAY, LOGIN,EMAIL) VALUES (?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getBirthday(), user.getLogin(), user.getEmail());
        return user;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM FILMS.USERS WHERE id = ?", id);
    }

    @Override
    public User change(User user) {
        Long id = user.getId();
        if (user.getId() < 1) {
            throw new IncorrectParameterException("id");
        }
        jdbcTemplate.update("update FILMS.USERS set email = ? where id = ?", user.getEmail(), id);
        jdbcTemplate.update("update FILMS.USERS set login = ? where id = ?", user.getLogin(), id);
        jdbcTemplate.update("update FILMS.USERS set name = ? where id = ?", user.getName(), id);
        jdbcTemplate.update("update FILMS.USERS set birthday = ? where id = ?", user.getBirthday(), id);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM FILMS.USERS");
        while (sqlRowSet.next()) {
            User user = new User(
                    sqlRowSet.getString("email"),
                    sqlRowSet.getString("login"),
                    sqlRowSet.getString("name"),
                    sqlRowSet.getDate("birthday").toLocalDate());
            user.setId(sqlRowSet.getLong("id"));
            list.add(user);
        }

        return list;

    }

    @Override
    public User get(Long id) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM FILMS.USERS f where f.id = ?", id);
        User user = null;
        if (sqlRowSet.next()) {
            user = new User(
                    sqlRowSet.getString("email"),
                    sqlRowSet.getString("login"),
                    sqlRowSet.getString("name"),
                    sqlRowSet.getDate("birthday").toLocalDate());
            user.setId(id);
        }
        return user;
    }

}
