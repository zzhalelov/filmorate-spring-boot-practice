package kz.zzhalelov.filmoratespringbootpractice.storage;

import kz.zzhalelov.filmoratespringbootpractice.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    Optional<Genre> findById(int id);

    List<Genre> findAll();

    List<Genre> findAllByFilmId(int id);
}