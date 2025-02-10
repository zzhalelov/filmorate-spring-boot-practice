package kz.zzhalelov.filmoratespringbootpractice.storage.impl;

import kz.zzhalelov.filmoratespringbootpractice.model.Genre;
import kz.zzhalelov.filmoratespringbootpractice.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Genre> findById(int id) {
        return jdbcTemplate.query("select * from genres where id = ?", this::mapRow, id)
                .stream()
                .findFirst();
    }

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query("select * from genres", this::mapRow);
    }

    @Override
    public List<Genre> findAllByFilmId(int filmId) {
        return jdbcTemplate.query("select genres.* from genres join film_genres on genres.id = film_genres.genre_id where film_genres.film_id=?", this::mapRow, filmId);
    }

    private Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }
}