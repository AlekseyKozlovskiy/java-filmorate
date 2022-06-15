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
import java.util.List;


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
        final String sql = "UPDATE FILMS.FILMS SET name = ?, description = ?, release_date = ?, duration = ?, rating = ?"
                + " WHERE id = ?;" +
                "update FILMS.GENRE set GENRE_ID = ? where FILM_ID = ?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRate(), film.getId(),film.getMpa().getId(), id);
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
    public Film findById(Long id) {
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

