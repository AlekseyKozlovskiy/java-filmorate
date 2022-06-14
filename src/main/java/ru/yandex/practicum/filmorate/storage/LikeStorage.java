package ru.yandex.practicum.filmorate.storage;

import model.Film;

import java.util.List;

public interface LikeStorage {
    public void addLike(Long id, Long userId);


    public void deleteLike(Long filmId, Long userId);


    public List<Film> getPopularFilm(Integer count);

}
