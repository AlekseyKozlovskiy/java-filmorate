package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Film {
    private long id;
//    @NotBlank
    private String name;

//    @Length(max = 200)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @JsonSerialize(using = DurationSerialize.class)
    @JsonDeserialize(using = DurationDeserialize.class)
    private Duration duration;

    public Film(String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = NumberGenerator.getFilmId();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}