package kz.zzhalelov.filmoratespringbootpractice.service;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.exception.ValidateException;
import kz.zzhalelov.filmoratespringbootpractice.model.User;
import kz.zzhalelov.filmoratespringbootpractice.storage.FriendStorage;
import kz.zzhalelov.filmoratespringbootpractice.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

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

    public void addFriend(int userId, int friendId) {
        // проверка, существует ли пользователь с идентификатором userId
        // проверка, существует ли пользователь с идентификатором friendId
        User user = findById(userId);
        User friend = findById(friendId);

        friendStorage.addFriendship(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        User user = findById(userId);
        User friend = findById(friendId);

        friendStorage.removeFriendship(userId, friendId);
    }

    public List<User> getFriends(int userId) {
        User user = findById(userId);
        return friendStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(int userId1, int userId2) {
        User user1 = findById(userId1);
        User user2 = findById(userId2);

        return friendStorage.getCommonFriends(userId1, userId2);
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