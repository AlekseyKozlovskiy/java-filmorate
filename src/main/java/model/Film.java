package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    long id;
    private Set<Long> userLikes = new HashSet<>();

    private String name;

    private Genre mpa;

    private Long rate;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private Long duration;


    public Film(String name, String description, LocalDate releaseDate, Long duration, Long rate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = new Genre();
    }
}