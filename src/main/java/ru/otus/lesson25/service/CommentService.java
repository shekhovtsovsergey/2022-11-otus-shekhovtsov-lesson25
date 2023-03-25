package ru.otus.lesson25.service;


import ru.otus.lesson25.dto.CommentDto;
import ru.otus.lesson25.exception.BookNotFoundException;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAllCommentsByBook(Long id) throws BookNotFoundException;

}
