package ru.yourpackage.userservice.service;

import ru.yourpackage.userservice.dao.UserDao;
import ru.yourpackage.userservice.model.User;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be null/blank");
        }
        return userDao.save(new User(null, email));
    }

    public void removeUser(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be null/blank");
        }
        userDao.deleteByEmail(email);
    }
}
