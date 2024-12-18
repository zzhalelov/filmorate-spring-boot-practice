package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> getAll() {
        films.add(new Film(1, "test", "description", LocalDate.of(2024, 1, 1), Duration.ofHours(2)));
        return films;
    }
}