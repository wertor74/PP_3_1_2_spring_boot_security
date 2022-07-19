package ru.wertor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wertor.spring.boot_security.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
