package ru.otus.lesson25.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.lesson25.model.User;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String name);

}

