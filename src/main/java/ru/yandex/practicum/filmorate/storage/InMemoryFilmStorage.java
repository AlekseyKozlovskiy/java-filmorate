package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import util.FilmValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> filmMap = new TreeMap<>();

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public Film findFilmById(Long id) {
        return filmMap.get(id);
    }

    @Override
    public Film create(Film film) {
        System.out.println(film.getId());
        if (FilmValidator.validate(film)) {
            filmMap.put(film.getId(), film);
            log.info("Добавлена запись: " + film);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;
    }


    @Override
    public Film change(Film film) {
        if (FilmValidator.validate(film)) {
            filmMap.put(film.getId(), film);
            log.info("Данные изменены");
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;

    }

    public Map<Long, Film> getFilmMap() {
        return filmMap;
    }

    @Override
    public void delete(Long id) {
        filmMap.remove(id);
    }
}
