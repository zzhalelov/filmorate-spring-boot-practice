package kz.zzhalelov.filmoratespringbootpractice.storage.impl;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.model.User;
import kz.zzhalelov.filmoratespringbootpractice.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map = Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday());
        int id = insert.executeAndReturnKey(map).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        int query = jdbcTemplate.update("update users set email=?, login=?, name=?, birthday=? where id = ?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        if (query == 0) {
            throw new NotFoundException("пользователь с id: " + user.getId() + " не существует");
        }
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return jdbcTemplate.query("select * from users where id = ?", this::mapRow, id)
                .stream()
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", this::mapRow);
    }

    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String login = rs.getString("login");
        String name = rs.getString("name");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, email, login, name, birthday, new HashMap<>());
    }
}