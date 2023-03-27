package ru.otus.lesson25.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.lesson25.model.Book;
import ru.otus.lesson25.model.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

    void deleteByBook(Book book);
}
