package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.exception.FilmValidateException;
import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private int counter = 1;

    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film add(@RequestBody Film film) {
        validate(film);
        film.setId(getUniqueId());
        films.put(film.getId(), film);
        log.debug("Movie is added");
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        validate(film);
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Film is not found");
        }
        films.put(film.getId(), film);
        log.debug("Film is updated");
        return film;
    }

    @GetMapping
    public Collection<Film> getAll() {
        return films.values();
    }

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("название не может быть пустым");
            throw new FilmValidateException("название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.warn("максимальная длина описания — 200 символов");
            throw new FilmValidateException("максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("дата релиза — не раньше 28 декабря 1895 года");
            throw new FilmValidateException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.warn("продолжительность фильма должна быть положительной");
            throw new FilmValidateException("продолжительность фильма должна быть положительной");
        }
    }

    private int getUniqueId() {
        return counter++;
    }
}