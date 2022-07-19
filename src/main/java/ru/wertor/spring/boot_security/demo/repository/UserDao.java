package ru.wertor.spring.boot_security.demo.repository;

import ru.wertor.spring.boot_security.demo.model.User;

public interface UserDao {
    User findByLogin(String login);

    String getPasswordById(Long id);
}
