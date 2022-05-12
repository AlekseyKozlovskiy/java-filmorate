package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final FilmService filmService;

    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public ArrayList<Film> getAll() {
        return inMemoryFilmStorage.getFilms();
    }
    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new IncorrectParameterException(String.format("неверный id %d", id));
        }
        return inMemoryFilmStorage.getFilm(id);
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        return inMemoryFilmStorage.addFilm(film);
    }

    @PutMapping("/films")
    public Film change(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.changeFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") Long id,
                        @PathVariable("userId") Long userId) {
            if (!inMemoryFilmStorage.getFilmMap().containsKey(id)) {
                throw new IncorrectParameterException(String.format("неверный id %d", id));
            }
        return filmService.addLike(id, userId);
    }
    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteLike(@PathVariable("id") Long id,
                           @PathVariable("userId") Long userId) {
        if (userId < 0) {
            throw new IncorrectParameterException(String.format("неверный с id пользователя %d", id));
        }
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopularFilm(count);

    }
}