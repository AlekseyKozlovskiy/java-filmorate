package util;

import model.Film;

import java.time.LocalDate;

public class FilmValidator {
    public static boolean validate(Film film) {
        LocalDate date = LocalDate.of(1895, 12, 28);
        return !(film.getName() == null)
                && !(film.getName().isBlank())
                && (film.getDescription().length() <= 200)
                && !(film.getDescription().isBlank())
                && (film.getReleaseDate().isAfter(date))
                && (film.getDuration() > 0)
                && !(film.getMpa() == null);
    }
}
