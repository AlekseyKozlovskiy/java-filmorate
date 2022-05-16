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
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final FilmService filmService;

    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public ArrayList<Film> getAll() {
        return inMemoryFilmStorage.get();
    }
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new IncorrectParameterException(String.format("неверный id %d", id));
        }
        return inMemoryFilmStorage.get(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        return inMemoryFilmStorage.add(film);
    }

    @PutMapping
    public Film change(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.change(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") Long id,
                        @PathVariable("userId") Long userId) {
            if (!inMemoryFilmStorage.getFilmMap().containsKey(id)) {
                throw new IncorrectParameterException(String.format("неверный id %d", id));
            }
        return filmService.addLike(id, userId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable("id") Long id,
                           @PathVariable("userId") Long userId) {
        if (userId < 0) {
            throw new IncorrectParameterException(String.format("неверный с id пользователя %d", id));
        }
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopularFilm(count);
    }
    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new IncorrectParameterException(String.format("неверный id %d", id));
        }
        inMemoryFilmStorage.delete(id);
    }
}