package kz.zzhalelov.filmoratespringbootpractice.storage.impl;

import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import kz.zzhalelov.filmoratespringbootpractice.model.Genre;
import kz.zzhalelov.filmoratespringbootpractice.model.Rating;
import kz.zzhalelov.filmoratespringbootpractice.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map = Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "rating_id", film.getMpa().getId()
        );
        int id = insert.executeAndReturnKey(map).intValue();
        film.setId(id);
        insertGenresIntoFilms(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update("UPDATE films SET name = ?, " +
                        "description = ?, release_date = ?, " +
                        "duration = ?, rating_id = ? WHERE id = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        jdbcTemplate.update("DELETE FROM likes WHERE film_id = ?", film.getId());
        insertGenresIntoFilms(film);
        return film;
    }

    @Override
    public Optional<Film> findById(int id) {
        String sql = """
                select f.*,
                       r.name as rating_name
                from films f
                join ratings r on r.id = f.rating_id
                where f.id = ?
                """;
        return jdbcTemplate.query(sql, this::mapRow, id)
                .stream()
                .findFirst()
                .map(this::getGenresFromFilms);
    }

    @Override
    public List<Film> findAll() {
        String sql = """
                select f.*,
                       r.name as rating_name
                from films f
                join ratings r on r.id = f.rating_id
                """;
        List<Film> films = jdbcTemplate.query(sql, this::mapRow);
        films.forEach(this::getGenresFromFilms);
        return films;
    }

    @Override
    public void addLike(int filmId, int userId) {
        String sql = "insert into likes (film_id, user_id) values (?,?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        String sql = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getTotal(int count) {
        String sql = """
                select f.*,
                r.name as rating_name
                from films f
                join ratings r on r.id = f.rating_id
                left join likes l on f.id = l.film_id
                group by f.id, r.name
                order by count(l.film_id) desc ;
                """;
        return jdbcTemplate.query(sql, this::mapRow);
    }

    private Film getGenresFromFilms(Film film) {
        String sql = """
                select g.id, g.name from genres g
                join film_genres fg on g.id = fg.genre_id
                where fg.film_id = ?
                """;
        List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) -> new Genre(rs.getInt("id"), rs.getString("name")), film.getId());
        film.getGenres().addAll(genres);
        return film;
    }

    private void insertGenresIntoFilms(Film film) {
        String sql = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
        film.getGenres().forEach(genre ->
                jdbcTemplate.update(sql, film.getId(), genre.getId())
        );
    }

    private Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaId = rs.getInt("rating_id");
        String mpaName = rs.getString("rating_name");
        Rating mpa = new Rating(mpaId, mpaName);
        return new Film(id, name, description, releaseDate, duration, mpa);
    }
}