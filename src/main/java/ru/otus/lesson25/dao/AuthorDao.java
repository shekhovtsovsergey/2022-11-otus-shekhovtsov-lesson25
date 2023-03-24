package ru.otus.lesson25.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.lesson25.model.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {

}
