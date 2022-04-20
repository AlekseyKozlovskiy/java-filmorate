package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import model.FilmUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Map;
import java.util.TreeMap;

@RestController
@Slf4j
public class FilmController {
    private Map<Long, Film> filmMap = new TreeMap<>();

    @GetMapping("/films")
    public Map<Long, Film> findAll() {
        return filmMap;
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        if (FilmUtils.chek(film)) {
            filmMap.put(film.getId(), film);
            log.info("Добавлена запись: " + film);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;
    }

    @PutMapping("/films")
    public Film change(@Valid @RequestBody Film film) {
        if (FilmUtils.chek(film)) {
            filmMap.put(film.getId(), film);
            log.info("Данные изменены");
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;
    }
}
