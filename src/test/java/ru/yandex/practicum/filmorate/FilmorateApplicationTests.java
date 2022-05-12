package ru.yandex.practicum.filmorate;

import model.Film;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.Duration;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {
    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
//    public FilmorateApplicationTests(InMemoryFilmStorage inMemoryFilmStorage) {
//        this.inMemoryFilmStorage = inMemoryFilmStorage;
//    }


    @Test
    void checkValidUserLogin() {
        User a = new User("mail@mail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertTrue(inMemoryUserStorage.validate(a));
        User b = new User("mail@mail.ru", " ", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(inMemoryUserStorage.validate(b));
        User c = new User("mail@mail.ru", null, "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(inMemoryUserStorage.validate(c));
    }

    @Test
    void checkValidUserEmail() {
        User a = new User("mail@mail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertTrue(inMemoryUserStorage.validate(a));
        User b = new User("mailmail.ru", "dolore", "name",
                LocalDate.of(2020, 1, 15));
        Assertions.assertFalse(inMemoryUserStorage.validate(b));
    }

//    @Test
//    void checkValidFilmName() {
//        Film a = new Film("name", "description",
//                LocalDate.of(1895, 12, 29), Duration.ofMinutes(10L));
//        Assertions.assertTrue(inMemoryFilmStorage.validate(a));
//        Film b = new Film("name", "description",
//                LocalDate.of(1895, 12, 28), Duration.ofMinutes(10L));
//        Assertions.assertFalse(inMemoryFilmStorage.validate(b));
//        Film c = new Film(null, "description",
//                LocalDate.of(1895, 12, 29), Duration.ofMinutes(10L));
//        Assertions.assertFalse(inMemoryFilmStorage.validate(c));
//    }
//
//    @Test
//    void checkValidFilmDescription() {
//        String testString = "0123456789".repeat(20);
//
//        Film a = new Film("name", "description",
//                LocalDate.of(1895, 12, 29), Duration.ofMinutes(10L));
//        Assertions.assertTrue(inMemoryFilmStorage.validate(a));
//
//        Film b = new Film("name", testString,
//                LocalDate.of(1895, 12, 27), Duration.ofMinutes(10L));
//        Assertions.assertFalse(inMemoryFilmStorage.validate(b));
//    }
//
//    @Test
//    void checkValidFilmDuration() {
//        Film a = new Film("name", "description",
//                LocalDate.of(1895, 12, 29), Duration.ofMinutes(10L));
//        Assertions.assertTrue(inMemoryFilmStorage.validate(a));
//
//        Film b = new Film("name", "description",
//                LocalDate.of(1895, 12, 27), Duration.ofMinutes(-10L));
//        Assertions.assertFalse(inMemoryFilmStorage.validate(b));
//
//    }
}