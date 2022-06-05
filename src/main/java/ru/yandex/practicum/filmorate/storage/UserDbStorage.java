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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public void addFriend(Long id, Long friendId) {
        SqlRowSet sqlRowSet = jdbcTemplate
                .queryForRowSet("SELECT * FROM FILMS.FRIENDS f2 WHERE USER_ID = ? AND FRIEND_ID = ?", friendId, id);
        if (!sqlRowSet.next()) {
            System.out.println("ura, mother fuck");
            jdbcTemplate.update("INSERT INTO FILMS.FRIENDS (USER_ID, FRIEND_ID, APPROOVED) VALUES (?, ?, ?)",
                    id, friendId, "waiting");
        } else {
            jdbcTemplate
                    .update("UPDATE FILMS.FRIENDS SET APPROOVED = 'approved' WHERE USER_ID = ? AND FRIEND_ID = ?",
                            friendId, id);
            jdbcTemplate.update("INSERT INTO FILMS.FRIENDS (USER_ID, FRIEND_ID, APPROOVED) VALUES (?, ?, ?)",
                    id, friendId, "approved");
        }
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        jdbcTemplate.update("DELETE FROM FILMS.FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?", id, friendId);
        jdbcTemplate.update("DELETE FROM FILMS.FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?", friendId, id);

    }

    @Override
    public Set<User> getAllFriends(Long id) {
        Set<User> userSet = new HashSet<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT friend_id\n" +
                "FROM FILMS.FRIENDS f \n" +
                "WHERE USER_ID = ?", id);
        while (sqlRowSet.next()) {
            System.out.println(sqlRowSet.getMetaData());
            userSet.add(get(sqlRowSet.getLong("friend_id")));
        }
        return userSet;
    }

    @Override
    public Set<User> getMutualFriends(Long id, Long friendId) {
        Set<User> use = getAllFriends(id);
        use.retainAll(getAllFriends(friendId));
        return use;
    }
}
