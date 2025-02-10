package kz.zzhalelov.filmoratespringbootpractice.storage.impl;

import kz.zzhalelov.filmoratespringbootpractice.model.User;
import kz.zzhalelov.filmoratespringbootpractice.storage.FriendStorage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Component
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriendship(int userId, int friendId) {
        String sql = "insert into friends (user_id, friend_id) values (?,?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void removeFriendship(int userId, int friendId) {
        String sql = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> getFriends(int userId) {
        String sql = """
                select *
                from users
                join friends on users.id = friends.friend_id
                where user_id = ?;
                """;
        return jdbcTemplate.query(sql, this::mapRow, userId);
    }

    @Override
    public List<User> getCommonFriends(int userId, int friendId) {
        String sql = """
                select u.*
                from users u
                join friends f1 on u.id = f1.friend_id
                join friends f2 on u.id = f2.friend_id
                where  f1.user_id = ? and  f2.user_id = ?
                """;
        return jdbcTemplate.query(sql, this::mapRow, userId, friendId);
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