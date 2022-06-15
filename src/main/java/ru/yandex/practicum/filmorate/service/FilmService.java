package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import model.Film;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film change(Film film) {
        return filmStorage.change(film);
    }

    public void delete(Long id) {
        filmStorage.delete(id);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public void addLike(Long id, Long userId) {
        likeStorage.add(id, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        likeStorage.delete(filmId, userId);
    }

    public List<Film> getPopularFilm(Integer count) {
        return likeStorage.getPopular(count);
    }

    public Film findFilmById(Long id) {
        return filmStorage.findById(id);
    }

}
