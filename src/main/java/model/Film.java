package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
//@RequiredArgsConstructor
public class Film {
    long id;
    private Set<Long> userLikes = new HashSet<>();

    //    @NotBlank
    private String name;

    private Genre mpa;

    private Long rate;

    //    @Length(max = 200)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    //    @JsonSerialize(using = DurationSerialize.class)
//    @JsonDeserialize(using = DurationDeserialize.class)
    private Long duration;


    public Film(String name, String description, LocalDate releaseDate, Long duration, Long rate) {
//        this.id = NumberGenerator.getFilmId();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = new Genre();
    }
}