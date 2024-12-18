package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.exception.FilmValidateException;
import kz.zzhalelov.filmoratespringbootpractice.exception.UserValidateException;
import kz.zzhalelov.filmoratespringbootpractice.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public User add(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new UserValidateException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new UserValidateException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new UserValidateException("имя для отображения может быть пустым — в таком случае будет использован логин");
        }
        if (user.getBirthdate().isAfter(LocalDate.now())) {
            throw new UserValidateException("дата рождения не может быть в будущем");
        }
        users.add(user);
        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        users.add(user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return users;
    }
}