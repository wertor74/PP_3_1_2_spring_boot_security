package ru.wertor.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wertor.spring.boot_security.demo.model.User;
import ru.wertor.spring.boot_security.demo.repository.UserDao;
import ru.wertor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImp(UserRepository userRepository, UserDao userDao, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    @Override
    public List<User> findAll () {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        user.setRole(user.getRole());
        System.out.println("user.getPassword() = " + user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        user.setRole(user.getRole());
        if (!user.getPassword().equals(userDao.getPasswordById(user.getId()))) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteById (Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

    // получаем Id зарегистрированного пользователя
    @Transactional
    @Override
    public Long loggedUserId() {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedUser.getId();
    }
}