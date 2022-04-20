package model;

import java.time.LocalDate;

public class FilmUtils {
    public static boolean chek (Film film){
        LocalDate date = LocalDate.of(1895, 12, 28);

        if (film.getName() == null || film.getName().isBlank()) {
            return false;
        } else if (!(film.getDescription().length() <= 200)){
            return false;
        } else if (film.getReleaseDate().isBefore(date)){
            return false;
        } else if (film.getDuration().isNegative()) {
            return false;
        }
        return true;
    }
}