package ru.otus.lesson25.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.lesson25.dto.GenreDto;
import ru.otus.lesson25.exception.GenreNotFoundException;
import ru.otus.lesson25.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/api/v1/genre")
    public List<GenreDto> getGenreList() throws GenreNotFoundException {
        return genreService.getAllGenre();
    }

    @ExceptionHandler({GenreNotFoundException.class})
    private ResponseEntity<String> handleNotFound(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
