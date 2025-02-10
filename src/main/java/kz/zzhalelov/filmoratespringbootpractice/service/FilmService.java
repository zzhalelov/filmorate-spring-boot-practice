package kz.zzhalelov.filmoratespringbootpractice.service;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.exception.ValidateException;
import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import kz.zzhalelov.filmoratespringbootpractice.model.Genre;
import kz.zzhalelov.filmoratespringbootpractice.model.User;
import kz.zzhalelov.filmoratespringbootpractice.storage.FilmStorage;
import kz.zzhalelov.filmoratespringbootpractice.storage.GenreStorage;
import kz.zzhalelov.filmoratespringbootpractice.storage.RatingStorage;
import kz.zzhalelov.filmoratespringbootpractice.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final RatingStorage ratingStorage;
    private final GenreStorage genreStorage;

    private final UserStorage userStorage;

    public Film create(Film film) {
        validate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        validate(film);
        findFilmById(film.getId());
        return filmStorage.update(film);
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public void addLike(int filmId, int userId) {
        Film film = findFilmById(filmId);
        User user = findUserById(userId);

        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        Film film = findFilmById(filmId);
        User user = findUserById(userId);

        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getTopFilms(int count) {
        List<Film> films = filmStorage.findAll();

        return filmStorage.getTotal(count);
    }

    public Film findFilmById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Film not found"));
    }

    public User findUserById(int id) {
        return userStorage.findById(id).orElseThrow(() -> new NotFoundException("User Not found"));
    }

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("название не может быть пустым");
            throw new ValidateException("название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.warn("максимальная длина описания — 200 символов");
            throw new ValidateException("максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidateException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.warn("продолжительность фильма должна быть положительной");
            throw new ValidateException("продолжительность фильма должна быть положительной");
        }
        ratingStorage.findById(film.getMpa().getId())
                .orElseThrow(() -> new ValidateException("Рейтинг не найден"));

        for (Genre genre : film.getGenres()) {
            genreStorage.findById(genre.getId())
                    .orElseThrow(() -> new ValidateException("Жанр не найден"));
        }
    }
}