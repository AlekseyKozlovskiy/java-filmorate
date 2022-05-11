package model;

public class NumberGenerator {
    private static long userId = 1;
    private static long filmId = 1;

    public static long getUserId() {
        return userId++;
    }
    public static long getFilmId() {
        return filmId++;
    }
}
