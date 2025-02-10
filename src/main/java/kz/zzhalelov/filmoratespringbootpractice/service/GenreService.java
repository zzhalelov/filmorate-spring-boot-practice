package kz.zzhalelov.filmoratespringbootpractice.service;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.model.Genre;
import kz.zzhalelov.filmoratespringbootpractice.storage.GenreStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre findById(int genreId) {
        return genreStorage.findById(genreId).orElseThrow(() -> new NotFoundException("Жанр не найден"));
    }

    public List<Genre> findAll() {
        return genreStorage.findAll();
    }
}
