package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import util.UserValidator;

import java.time.LocalDate;

@SpringBootTest
//@Sql("src/test/resources/population/population.sql")
public class FilmorateApplicationTests {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private InMemoryFilmStorage inMemoryFilmStorage;
    @Autowired
    private InMemoryUserStorage inMemoryUserStorage;


    @Test
    void checkValidUserLogin() {
        User a = new User("mail@mail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertTrue(UserValidator.validate(a));
        User b = new User("mail@mail.ru", " ", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(UserValidator.validate(b));
        User c = new User("mail@mail.ru", null, "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(UserValidator.validate(c));
    }

    @Test
    void checkValidUserEmail() {
        User a = new User("mail@mail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertTrue(UserValidator.validate(a));
        User b = new User("mailmail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(UserValidator.validate(b));
    }
}