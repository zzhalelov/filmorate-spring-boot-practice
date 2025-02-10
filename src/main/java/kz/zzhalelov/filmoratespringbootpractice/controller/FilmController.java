package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import kz.zzhalelov.filmoratespringbootpractice.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film add(@RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam int count) {
        return filmService.getTopFilms(count);
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    @PutMapping("/{filmId}/like/{id}")
    public void addLike(@PathVariable int filmId,
                        @PathVariable int id) {
        filmService.addLike(filmId, id);
    }

    @DeleteMapping("/{filmId}/like/{id}")
    public void removeLike(@PathVariable int filmId,
                           @PathVariable int id) {
        filmService.removeLike(filmId, id);
    }
}