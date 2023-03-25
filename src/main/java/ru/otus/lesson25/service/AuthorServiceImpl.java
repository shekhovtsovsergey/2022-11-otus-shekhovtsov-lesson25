package ru.otus.lesson25.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.lesson25.converter.AuthorConverter;
import ru.otus.lesson25.dao.AuthorDao;
import ru.otus.lesson25.dto.AuthorDto;
import ru.otus.lesson25.exception.AuthorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService{

    private final AuthorDao authorDao;
    private final AuthorConverter authorConverter;

    @Override
    public List<AuthorDto> getAllAuthore() throws AuthorNotFoundException {
        return authorDao.findAll().stream().map(authorConverter::entityToDto).collect(Collectors.toList());
    }

}
