package ru.yandex.practicum.filmorate.dbstorage;

import lombok.RequiredArgsConstructor;
import model.Film;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.yandex.practicum.filmorate.FilmorateApplicationTests;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest extends BaseDbTest {

    private final FilmStorage filmStorage;
//    private final FilmDao filmDao;

    @Test
    public void createFilmTest() {
        Film film = new Film("labore nulla", "Duis in consequat esse",
                LocalDate.of(1979, 4, 7), 100L, 4L);
        film.getMpa().setId(1L);
        filmStorage.create(film);
        Assertions.assertEquals(3, filmStorage.getAll().size());
    }

    @Test
    public void changeFilmTest() {
        Film film = new Film("new nulla", "New Duis in consequat esse",
                LocalDate.of(1979, 4, 7), 100L, 4L);
        film.getMpa().setId(1L);
        film.setId(100L);
        filmStorage.change(film);
        Optional<Film> filmOptional = Optional.of(filmStorage.findFilmById(100L));
        assertThat(filmOptional).isPresent()
                .hasValueSatisfying(user1 -> assertThat(user1)
                        .hasFieldOrPropertyWithValue("name", "new nulla"));
    }

    @Test
    public void deleteFilmTest() {
        filmStorage.delete(100L);
        assertEquals(2, filmStorage.getAll().size());
    }

    @Test
    public void getAllTest() {
        assertEquals(3, filmStorage.getAll().size());
    }

    @Test
    public void addLikeTest(){
        filmStorage.addLike(100L, 102L);
        Film film = filmStorage.getPopularFilm(1).get(0);
        assertEquals("film1", film.getName());
    }

    @Test
    public void deleteLikeTest(){
        filmStorage.deleteLike(100L, 100L);
        filmStorage.deleteLike(100L, 101L);
        assertEquals("film2", filmStorage.getPopularFilm(1).get(0).getName() );
    }
    @Test
    public void getPopularFilm() {
        assertEquals("film1", filmStorage.getPopularFilm(1).get(0).getName());

    }

    @Test
    public void findFilmById(){
        assertEquals("film2", filmStorage.findFilmById(101L).getName());
    }

}
