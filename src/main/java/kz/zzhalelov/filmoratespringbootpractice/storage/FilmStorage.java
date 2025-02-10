package kz.zzhalelov.filmoratespringbootpractice.storage;

import kz.zzhalelov.filmoratespringbootpractice.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    Optional<Film> findById(int id);

    List<Film> findAll();

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    List<Film> getTotal(int count);
}