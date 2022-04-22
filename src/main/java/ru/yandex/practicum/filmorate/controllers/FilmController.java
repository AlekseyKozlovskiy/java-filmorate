package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@RestController
@Slf4j
public class FilmController {
    private Map<Long, Film> filmMap = new TreeMap<>();

    @GetMapping("/films")
    public ArrayList<Film> getAll() {
        return new ArrayList<Film>(filmMap.values());
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        if (validate(film)) {
            filmMap.put(film.getId(), film);
            log.info("Добавлена запись: " + film);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;
    }

    @PutMapping("/films")
    public Film change(@Valid @RequestBody Film film) {
        if (validate(film)) {
            filmMap.put(film.getId(), film);
            log.info("Данные изменены");
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;
    }

    public static boolean validate(Film film) {
        LocalDate date = LocalDate.of(1895, 12, 28);

        return !(film.getName() == null)
                && !(film.getName().isBlank())
                && (film.getDescription().length() <= 200)
                && (film.getReleaseDate().isAfter(date));
    }
}
