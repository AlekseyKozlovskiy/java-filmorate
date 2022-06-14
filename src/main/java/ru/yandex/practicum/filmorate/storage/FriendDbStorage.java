package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;
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
            userSet.add(userStorage.get(sqlRowSet.getLong("friend_id")));
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
