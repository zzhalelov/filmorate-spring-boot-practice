package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.exception.ValidateException;
import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import kz.zzhalelov.filmoratespringbootpractice.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
//        validate(film);
//        if (!films.containsKey(film.getId())) {
//            throw new NotFoundException("Film is not found");
//        }
//        films.put(film.getId(), film);
//        log.debug("Film is updated");
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


    //    private int getUniqueId() {
//        return counter++;
//    }
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