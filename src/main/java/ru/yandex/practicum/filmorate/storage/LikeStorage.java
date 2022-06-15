package ru.yandex.practicum.filmorate.storage;

import model.Film;

import java.util.List;

public interface LikeStorage {
    void add(Long id, Long userId);


    void delete(Long filmId, Long userId);


    List<Film> getPopular(Integer count);

}
