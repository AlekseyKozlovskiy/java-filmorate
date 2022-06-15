package ru.yandex.practicum.filmorate.storage;

import model.Film;

import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film change(Film film);

    void delete(Long id);

    List<Film> getAll();

    Film findById(Long id);

}
