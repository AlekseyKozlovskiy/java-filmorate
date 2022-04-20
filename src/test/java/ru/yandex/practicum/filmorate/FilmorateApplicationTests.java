package ru.yandex.practicum.filmorate;

import model.Film;
import model.FilmUtils;
import model.User;
import model.UserUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {


    @Test
    void checkValidUserLogin() {
        User a = new User("mail@mail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertTrue(UserUtils.chek(a));
        User b = new User("mail@mail.ru", " ", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(UserUtils.chek(b));
        User c = new User("mail@mail.ru", null, "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(UserUtils.chek(c));
    }

    @Test
    void checkValidUserEmail() {
        User a = new User("mail@mail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertTrue(UserUtils.chek(a));
        User b = new User("mailmail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(UserUtils.chek(b));
    }

    @Test
    void checkValidFilmName() {
        Film a = new Film("name", "description",
                LocalDate.of(1895, 12, 28), Duration.ofMinutes(10L));
        Assertions.assertTrue(FilmUtils.chek(a));
        Film b = new Film("name", "description",
                LocalDate.of(1895, 12, 27), Duration.ofMinutes(10L));
        Assertions.assertFalse(FilmUtils.chek(b));
        Film c = new Film(null, "description",
                LocalDate.of(1895, 12, 27), Duration.ofMinutes(10L));
        Assertions.assertFalse(FilmUtils.chek(c));
    }

    @Test
    void checkValidFilmDescription() {
        String testString = "0123456789".repeat(20);

        Film a = new Film("name", "description",
                LocalDate.of(1895, 12, 28), Duration.ofMinutes(10L));
        Assertions.assertTrue(FilmUtils.chek(a));

        Film b = new Film("name", testString,
                LocalDate.of(1895, 12, 27), Duration.ofMinutes(10L));
        Assertions.assertFalse(FilmUtils.chek(b));
    }

    @Test
    void checkValidFilmDuration() {
        Film a = new Film("name", "description",
                LocalDate.of(1895, 12, 28), Duration.ofMinutes(10L));
        Assertions.assertTrue(FilmUtils.chek(a));

        Film b = new Film("name", "description",
                LocalDate.of(1895, 12, 27), Duration.ofMinutes(-10L));
        Assertions.assertFalse(FilmUtils.chek(b));

    }
}