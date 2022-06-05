package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import model.Film;
import model.NumberGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import util.FilmValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film create(Film film) {
        if (!FilmValidator.validate(film)) {
            throw new ValidationException("данные не верны");
        }
        film.setId(NumberGenerator.getFilmId());
        jdbcTemplate.update("INSERT INTO FILMS.FILMS (ID, NAME, DESCRIPTION, RELEASE_DATE,DURATION,RATING)" +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getRate());


        jdbcTemplate.update("INSERT INTO FILMS.GENRE (GENRE_ID, FILM_ID) VALUES (?, ?)",
                film.getMpa().getId(), film.getId());
        return film;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM FILMS.FILMS WHERE id = ?", id);
    }

    @Override
    public Film change(Film film) {
        Long id = film.getId();
        if (film.getId() < 0) {
            throw new IncorrectParameterException("id");
        }
        jdbcTemplate.update("update FILMS.FILMS set name = ? where id = ?", film.getName(), id);
        jdbcTemplate.update("update FILMS.FILMS set description = ? where id = ?", film.getDescription(), id);
        jdbcTemplate.update("update FILMS.FILMS set release_date = ? where id = ?", film.getReleaseDate(), id);
        jdbcTemplate.update("update FILMS.FILMS set duration = ? where id = ?", film.getDuration(), id);
        jdbcTemplate.update("update FILMS.FILMS set rating = ? where id = ?", film.getRate().toString(), id);
        jdbcTemplate.update("update FILMS.GENRE set GENRE_ID = ? where FILM_ID = ?", film.getMpa().getId(), id);
        return film;
    }

    @Override
    public List<Film> getAll() {
        List<Film> filmList = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM FILMS.FILMS");

        while (sqlRowSet.next()) {
            SqlRowSet sqlRowSet2;
            Film film = new Film(sqlRowSet.getString("name"),
                    sqlRowSet.getString("description"),
                    sqlRowSet.getDate("release_date").toLocalDate(),
                    sqlRowSet.getLong("duration"),
                    sqlRowSet.getLong("rating"));


            Long id = sqlRowSet.getLong("id");

            film.setId(id);
            sqlRowSet2 = jdbcTemplate.queryForRowSet("SELECT GENRE_ID FROM FILMS.GENRE WHERE FILM_ID = ?", id);
            while (sqlRowSet2.next()) {
                film.getMpa().setId(sqlRowSet2.getLong("genre_id"));
            }
            sqlRowSet2 = jdbcTemplate.queryForRowSet("SELECT USER_ID \n" +
                    "FROM FILMS.LIKES l \n" +
                    "WHERE FILM_ID = ?", id);
            while (sqlRowSet2.next()) {
                film.getUserLikes().add(sqlRowSet2.getLong("user_id"));
            }
            filmList.add(film);
        }
        return filmList;
    }

    @Override
    public void addLike(Long id, Long userId) {
        jdbcTemplate.update("INSERT INTO FILMS.LIKES (USER_ID, FILM_ID) VALUES (?, ?)", userId, id);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        jdbcTemplate.update("DELETE FROM FILMS.LIKES WHERE FILM_ID = ? AND USER_ID = ?", filmId, userId);
    }

    @Override
    public List<Film> getPopularFilm(Integer count) {
        List<Film> filmList = getAll();
        filmList.sort((o1, o2) -> o2.getUserLikes().size() - o1.getUserLikes().size());
        return filmList.stream().limit(count).collect(Collectors.toList());
    }

    @Override
    public Film findFilmById(Long id) {
        if (id < 1) {
            throw new IncorrectParameterException("id");
        }
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM FILMS.films f where f.id = ?", id);
        Film film = null;
        SqlRowSet sqlRowSet2;
        if (sqlRowSet.next()) {
            film = new Film(sqlRowSet.getString("name"),
                    sqlRowSet.getString("description"),
                    sqlRowSet.getDate("release_date").toLocalDate(),
                    sqlRowSet.getLong("duration"),
                    sqlRowSet.getLong("rating"));

            film.setId(id);
            sqlRowSet2 = jdbcTemplate.queryForRowSet("SELECT GENRE_ID FROM FILMS.GENRE WHERE FILM_ID = ?", id);

            while (sqlRowSet2.next()) {
                film.getMpa().setId(sqlRowSet2.getLong("genre_id"));
            }
            sqlRowSet2 = jdbcTemplate.queryForRowSet("SELECT USER_ID \n" +
                    "FROM FILMS.LIKES l \n" +
                    "WHERE FILM_ID = ?", id);
            while (sqlRowSet2.next()) {
                film.getUserLikes().add(sqlRowSet2.getLong("user_id"));
            }

        }
        return film;
    }

}
