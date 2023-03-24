package ru.otus.lesson25.service;


import ru.otus.lesson25.dto.GenreDto;
import ru.otus.lesson25.exception.GenreNotFoundException;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenre() throws GenreNotFoundException;
    GenreDto getGenreById(Long id) throws GenreNotFoundException;


}
