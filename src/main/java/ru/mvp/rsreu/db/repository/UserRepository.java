package ru.mvp.rsreu.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mvp.rsreu.db.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
