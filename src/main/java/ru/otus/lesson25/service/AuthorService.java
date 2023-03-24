package ru.otus.lesson25.service;


import ru.otus.lesson25.dto.AuthorDto;
import ru.otus.lesson25.exception.AuthorNotFoundException;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthore() throws AuthorNotFoundException;

}
