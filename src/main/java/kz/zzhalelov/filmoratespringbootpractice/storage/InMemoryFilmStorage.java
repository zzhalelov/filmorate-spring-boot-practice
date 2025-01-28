package kz.zzhalelov.filmoratespringbootpractice.storage;

import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int counter = 1;

    @Override
    public Film create(Film film) {
        film.setId(getUniqueId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
//        if (!films.containsKey(film.getId())) {
//            throw new NotFoundException("Film not found");
//        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    private int getUniqueId() {
        return counter++;
    }
}
