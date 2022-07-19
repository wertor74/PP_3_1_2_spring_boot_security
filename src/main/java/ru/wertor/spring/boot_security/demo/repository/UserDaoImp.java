package ru.wertor.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.wertor.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class UserDaoImp implements UserDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.role r WHERE u.login = :login", User.class)
                .setParameter("login", login);
        return query.getSingleResult();
    }

    @Override
    public String getPasswordById(Long id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id);
        return query.getSingleResult().getPassword();
    }
}
