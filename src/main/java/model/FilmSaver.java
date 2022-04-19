package model;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class FilmSaver {
    private Map<Long, Film> filmMap = new TreeMap<>();

}
