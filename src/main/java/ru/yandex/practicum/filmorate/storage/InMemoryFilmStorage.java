package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Long, Film> filmMap = new TreeMap<>();

    @Override
    public ArrayList<Film> get() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public Film get(Long id) {
        return filmMap.get(id);
    }

    @Override
    public Film add(Film film) {
        System.out.println(film.getId());
        if (validate(film)) {
            filmMap.put(film.getId(), film);
            log.info("Добавлена запись: " + film);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;
    }


    @Override
    public Film change(Film film) {
        if (validate(film)) {
            filmMap.put(film.getId(), film);
            log.info("Данные изменены");
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;

    }

    @Override
    public boolean validate(Film film) {
        LocalDate date = LocalDate.of(1895, 12, 28);

        return !(film.getName() == null)
                && !(film.getName().isBlank())
                && (film.getDescription().length() <= 200)
                && !(film.getDescription().isBlank())
                && (film.getReleaseDate().isAfter(date))
                && (film.getDuration() > 0);
    }

    public Map<Long, Film> getFilmMap() {
        return filmMap;
    }

    @Override
    public void delete(Long id) {
        filmMap.remove(id);
    }
}
