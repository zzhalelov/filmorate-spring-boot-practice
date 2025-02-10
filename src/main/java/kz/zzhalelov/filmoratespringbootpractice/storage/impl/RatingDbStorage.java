package kz.zzhalelov.filmoratespringbootpractice.storage.impl;

import kz.zzhalelov.filmoratespringbootpractice.model.Rating;
import kz.zzhalelov.filmoratespringbootpractice.storage.RatingStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RatingDbStorage implements RatingStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Rating> findById(int id) {
        return jdbcTemplate.query("select * from ratings where id = ?", this::mapRow, id)
                .stream()
                .findFirst();
    }

    @Override
    public List<Rating> findAll() {
        return jdbcTemplate.query("select * from ratings", this::mapRow);
    }

    private Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Rating(id, name);
    }
}