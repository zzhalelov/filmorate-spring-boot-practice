package kz.zzhalelov.filmoratespringbootpractice.service;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.exception.ValidateException;
import kz.zzhalelov.filmoratespringbootpractice.model.User;
import kz.zzhalelov.filmoratespringbootpractice.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User findById(int id) {
        return userStorage.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User create(User user) {
        // валидация, проверка
        validate(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        validate(user);
        findById(user.getId());
        return userStorage.update(user);
    }

    // добавления в друзья
    public void addFriend(int userId, int friendId) {
        // проверка, существует ли пользователь с идентификатором userId
        // проверка, существует ли пользователь с идентификатором friendId
        User user = findById(userId);
        User friend = findById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        userStorage.update(user);
        userStorage.update(friend);
    }

    // удаление друга
    public void removeFriend(int userId, int friendId) {
        User user = findById(userId);
        User friend = findById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        userStorage.update(user);
        userStorage.update(friend);
    }

    // Получение списка друзей определенного пользователя
    // Set<Integer> -> List<User>
    public List<User> getFriends(int userId) {
        User user = findById(userId);
        return user.getFriends().stream()
                .map(friendId -> findById(friendId))
                .collect(Collectors.toList());
    }

    // Получение списка общих друзей между двумя пользователями
    // Set<Integer> -> List<User>
    public List<User> getCommonFriends(int userId1, int userId2) {
        User user1 = findById(userId1);
        User user2 = findById(userId2);

        Set<Integer> commonFriends = user1.getFriends().stream()
                .filter(friendId -> user2.getFriends().contains(friendId))
                .collect(Collectors.toSet());

        return commonFriends.stream()
                .map(friendId -> findById(friendId))
                .collect(Collectors.toList());
    }

    private void validate(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidateException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidateException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidateException("дата рождения не может быть в будущем");
        }
    }
}