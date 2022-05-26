package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
//@RequiredArgsConstructor
public class Film {
    long id;
    private Set<Long> userLikes = new HashSet<>();

//    @NotBlank
    private String name;

    private Genre genre;

    private Rating rating;

//    @Length(max = 200)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

//    @JsonSerialize(using = DurationSerialize.class)
//    @JsonDeserialize(using = DurationDeserialize.class)
    private Long duration;


    public Film(String name, String description, LocalDate releaseDate, Long duration, Genre genre) {
        this.id = NumberGenerator.getFilmId();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genre = genre;
    }
}