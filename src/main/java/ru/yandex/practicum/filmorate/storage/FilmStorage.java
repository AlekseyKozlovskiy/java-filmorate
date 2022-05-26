package ru.yandex.practicum.filmorate.storage;

import model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> get();
    Film get(Long id);
    Film add(Film film);
    Film change(Film film);
    boolean validate(Film film);
    void delete(Long id);
}
