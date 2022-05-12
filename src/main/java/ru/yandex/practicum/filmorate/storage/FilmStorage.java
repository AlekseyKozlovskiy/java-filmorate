package ru.yandex.practicum.filmorate.storage;

import model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    ArrayList<Film> getFilms();
    Film getFilm(Long id);
    Film addFilm(Film film);
    Film changeFilm(Film film);
    boolean validate(Film film);
}
