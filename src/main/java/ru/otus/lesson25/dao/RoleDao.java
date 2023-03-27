package ru.otus.lesson25.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.lesson25.model.Role;

public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
