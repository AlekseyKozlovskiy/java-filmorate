package ru.yandex.practicum.filmorate.storage;

import model.Film;

import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film change(Film film);

    void delete(Long id);

    List<Film> getAll();

    void addLike(Long id, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Film> getPopularFilm(Integer count);

    Film findFilmById(Long id);

//    List<Film> get();


}
