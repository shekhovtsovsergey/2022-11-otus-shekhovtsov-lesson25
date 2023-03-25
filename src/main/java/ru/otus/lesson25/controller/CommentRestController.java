package ru.otus.lesson25.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.lesson25.dto.CommentDto;
import ru.otus.lesson25.exception.BookNotFoundException;
import ru.otus.lesson25.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/api/v1/book/{id}/comment")
    public List<CommentDto> getCommentList(@PathVariable(name = "id") Long id) throws BookNotFoundException {
        return commentService.getAllCommentsByBook(id);
    }

    @ExceptionHandler({BookNotFoundException.class})
    private ResponseEntity<String> handleNotFound(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
