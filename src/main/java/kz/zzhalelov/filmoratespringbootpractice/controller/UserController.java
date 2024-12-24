package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.exception.UserValidateException;
import kz.zzhalelov.filmoratespringbootpractice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private int counter = 1;

    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User add(@RequestBody User user) {
        validate(user);
        user.setId(getUniqueId());
        users.put(user.getId(), user);
        log.debug("User added");
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        validate(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("User not found");
        }
        users.put(user.getId(), user);
        log.debug("User is updated");
        return user;
    }

    @GetMapping
    public Collection<User> getAll() {
        return users.values();
    }

    private void validate(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("электронная почта не может быть пустой и должна содержать символ @");
            throw new UserValidateException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.warn("логин не может быть пустым и содержать пробелы");
            throw new UserValidateException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("дата рождения не может быть в будущем");
            throw new UserValidateException("дата рождения не может быть в будущем");
        }
    }

    private int getUniqueId() {
        return counter++;
    }
}