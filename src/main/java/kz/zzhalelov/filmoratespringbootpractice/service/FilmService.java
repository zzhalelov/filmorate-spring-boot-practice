package kz.zzhalelov.filmoratespringbootpractice.service;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.exception.ValidateException;
import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import kz.zzhalelov.filmoratespringbootpractice.model.User;
import kz.zzhalelov.filmoratespringbootpractice.storage.FilmStorage;
import kz.zzhalelov.filmoratespringbootpractice.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    private final UserStorage userStorage;

    //findall
    //findById
    //create
    //update

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

    // добавления лайка на определенным фильм, определенным пользователем
    public void addLike(int filmId, int userId) {
        Film film = findFilmById(filmId);
        User user = findUserById(userId);

        film.getLikes().add(userId);
    }

    // удаление лайка на определенным фильм, определенным пользователем
    public void removeLike(int filmId, int userId) {
        Film film = findFilmById(filmId);
        User user = findUserById(userId);

        film.getLikes().remove(userId);
    }

    // получение топ n популярных фильмов (список ограниченных в кол-во фильмов до n, сортированных по кол-во лайков по убыванию)
    public List<Film> getTopFilms(int n) {
        List<Film> films = filmStorage.findAll();
        List<Film> sortedFilms = films.stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(n)
                .toList();
        return sortedFilms;
    }

    public Film findFilmById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
    }

    public User findUserById(int id) {
        return userStorage.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
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
    }
}