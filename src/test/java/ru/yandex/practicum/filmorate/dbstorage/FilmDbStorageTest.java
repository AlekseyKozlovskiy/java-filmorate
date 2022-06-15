package ru.yandex.practicum.filmorate.dbstorage;

import lombok.RequiredArgsConstructor;
import model.Film;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest extends BaseDbTest {

    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
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
        Optional<Film> filmOptional = Optional.of(filmStorage.findById(100L));
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
        likeStorage.add(100L, 102L);
        Film film = likeStorage.getPopular(1).get(0);
        assertEquals("film1", film.getName());
    }

    @Test
    public void deleteLikeTest(){
        likeStorage.delete(100L, 100L);
        likeStorage.delete(100L, 101L);
        assertEquals("film2", likeStorage.getPopular(1).get(0).getName() );
    }
    @Test
    public void getPopularFilm() {
        assertEquals("film1", likeStorage.getPopular(1).get(0).getName());

    }

    @Test
    public void findFilmById(){
        assertEquals("film2", filmStorage.findById(101L).getName());
    }

}
