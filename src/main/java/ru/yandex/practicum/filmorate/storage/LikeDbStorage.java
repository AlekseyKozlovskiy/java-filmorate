package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import model.Film;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmStorage filmStorage;
    @Override
    public void add(Long id, Long userId) {
        jdbcTemplate.update("INSERT INTO FILMS.LIKES (USER_ID, FILM_ID) VALUES (?, ?)", userId, id);
    }

    @Override
    public void delete(Long filmId, Long userId) {
        jdbcTemplate.update("DELETE FROM FILMS.LIKES WHERE FILM_ID = ? AND USER_ID = ?", filmId, userId);
    }

    @Override
    public List<Film> getPopular(Integer count) {
        List<Film> filmList = filmStorage.getAll();
        filmList.sort((o1, o2) -> o2.getUserLikes().size() - o1.getUserLikes().size());
        return filmList.stream().limit(count).collect(Collectors.toList());
    }
}
