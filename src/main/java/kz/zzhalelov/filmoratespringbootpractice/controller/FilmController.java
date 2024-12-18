package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.exception.FilmValidateException;
import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    List<Film> films = new ArrayList<>();

    @PostMapping("/films")
    public Film add(@RequestBody Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new FilmValidateException("название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new FilmValidateException("максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmValidateException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration().isNegative()) {
            throw new FilmValidateException("продолжительность фильма должна быть положительной");
        }
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