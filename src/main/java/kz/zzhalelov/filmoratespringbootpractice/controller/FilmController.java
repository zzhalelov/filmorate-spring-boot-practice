package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    List<Film> films = new ArrayList<>();

    @PostMapping("/films")
    public Film add(@RequestBody Film film) {
        films.add(film);
        return film;
    }

    @PutMapping("/films")
    public Film update(@RequestBody Film film) {
        films.add(film);
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAll() {
        return films;
    }
}