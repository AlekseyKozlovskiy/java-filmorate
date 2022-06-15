package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping()
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok(filmService.create(film));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable("id") Long id) {
        return ResponseEntity.ok(filmService.findFilmById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Film>> getAll() {
        return ResponseEntity.ok(filmService.getAll());
    }

    @PutMapping()
    public ResponseEntity<Film> change(@RequestBody Film film) {
        return ResponseEntity.ok(filmService.change(film));
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable("id") Long id) {
        filmService.delete(id);
        ResponseEntity.ok();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") Long id,
                        @PathVariable("userId") Long userId) {
        filmService.addLike(id, userId);
        return null;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Long id,
                           @PathVariable("userId") Long userId) {
        if (userId < 0) {
            throw new IncorrectParameterException(String.format("неверный с id пользователя %d", id));
        }
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopularFilm(count);
    }
}