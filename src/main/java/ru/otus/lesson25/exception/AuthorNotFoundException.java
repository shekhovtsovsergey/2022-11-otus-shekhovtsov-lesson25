package ru.otus.lesson25.exception;

public class AuthorNotFoundException extends ObjectNotFoundException {

    public AuthorNotFoundException(Long genreId) {
        super(String.format("Author id %s not found", genreId));
    }

    public AuthorNotFoundException(String author_not_found) {
        super(author_not_found);
    }
}
