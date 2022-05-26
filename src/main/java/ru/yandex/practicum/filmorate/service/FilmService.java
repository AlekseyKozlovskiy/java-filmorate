package ru.yandex.practicum.filmorate.service;

import model.Film;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private InMemoryFilmStorage inMemoryFilmStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }
    public Film addLike(Long filmId, Long userId) {
        inMemoryFilmStorage.getFilmMap().get(filmId).getUserLikes().add(userId);
        return inMemoryFilmStorage.getFilmMap().get(filmId);
    }

    public Film deleteLike(Long filmId, Long userId) {
        inMemoryFilmStorage.getFilmMap().get(filmId).getUserLikes().remove(userId);
        return inMemoryFilmStorage.getFilmMap().get(filmId);
    }

    public List<Film> getPopularFilm(Integer count) {
        return inMemoryFilmStorage.getFilmMap().values().stream()
                .sorted(Comparator.comparing(e -> -e.getUserLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
