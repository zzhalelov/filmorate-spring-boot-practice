package kz.zzhalelov.filmoratespringbootpractice.storage;

import kz.zzhalelov.filmoratespringbootpractice.model.User;

import java.util.List;

public interface FriendStorage {
    void addFriendship(int userId, int friendId);

    void removeFriendship(int userId, int friendId);

    List<User> getFriends(int userId);

    List<User> getCommonFriends(int userId, int friendId);
}