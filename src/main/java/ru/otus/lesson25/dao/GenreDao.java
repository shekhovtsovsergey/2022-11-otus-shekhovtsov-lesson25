package ru.otus.lesson25.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.lesson25.model.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {

}