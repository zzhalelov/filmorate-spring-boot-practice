package kz.zzhalelov.filmoratespringbootpractice.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private final Set<Integer> likes = new HashSet<>(); // идентификаторы пользователей
    private final Set<Genre> genres = new HashSet<>();
    private Rating mpa;
}