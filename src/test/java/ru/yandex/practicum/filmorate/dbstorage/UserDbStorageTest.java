package ru.yandex.practicum.filmorate.dbstorage;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest extends BaseDbTest {

    private final UserStorage userDbStorage;
    private final FriendStorage friendStorage;

    @Test
    @SneakyThrows
    public void createUserTest() {

        User user = new User("mail@mail.ru", "lex", "Leha",
                LocalDate.of(2012, 1, 1));
        userDbStorage.create(user);
        assertEquals(4, userDbStorage.getAllUsers().size());

        Optional<User> userOptional = Optional.of(userDbStorage.get(1L));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user1 ->
                        assertThat(user1).hasFieldOrPropertyWithValue("id", 1L)
                );
    }

    @Test
    public void changeTest() {
        User userNew = new User("newMail@mail.ru", "newlex", "newLeha",
                LocalDate.of(2012, 8, 1));
        userNew.setId(100L);
        userDbStorage.change(userNew);
        List<User> allUsers = userDbStorage.getAllUsers();
        System.err.println(allUsers.size());
        allUsers.forEach(System.out::println);
        Optional<User> userOptional = Optional.of(userDbStorage.get(100L));
        assertThat(userOptional).isPresent()
                .hasValueSatisfying(user1 -> assertThat(user1)
                        .hasFieldOrPropertyWithValue("login", "newlex"));
    }

    @Test
    public void deleteTest() {
        userDbStorage.delete(100L);
        assertEquals(2, userDbStorage.getAllUsers().size());
    }

    @Test
    public void getAllUsersTest() {
        userDbStorage.getAllUsers();
        assertEquals(3, userDbStorage.getAllUsers().size());
    }

    @Test
    public void getTest() {
        User user = userDbStorage.get(100L);
        assertEquals("Sergey", user.getName());
    }

    @Test
    public void addFriendTest() {
        int friendCount = friendStorage.getAll(102l).size();
        friendStorage.add(102L, 101L);
        assertEquals(friendCount + 1, friendStorage.getAll(102l).size());
    }

    @Test
    public void deleteFriendTest() {
        assertEquals(2, friendStorage.getAll(100L).size());
        friendStorage.delete(100L, 101L);
        assertEquals(1, friendStorage.getAll(100L).size());
    }

    @Test
    public void getAllFriendsTest() {
        assertEquals(2, friendStorage.getAll(100L).size());
    }

    @Test
    public void getMutualFriendsTest() {
        assertEquals(1, friendStorage.getMutual(100L, 101L).size());
    }
}


