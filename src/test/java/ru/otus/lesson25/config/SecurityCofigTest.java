package ru.otus.lesson25.config;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.lesson25.service.AuthorService;
import ru.otus.lesson25.service.BookService;
import ru.otus.lesson25.service.CommentService;
import ru.otus.lesson25.service.GenreService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest()
@DisplayName("Контроллеры должны")
public class SecurityCofigTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;
    private final static String BOOK = "1";

    @ParameterizedTest
    @ValueSource(strings = {"/api/v1/author", "/api/v1/book", "/api/v1/comment", "/api/v1/genre"})
    @DisplayName("запрещать неаутентифицированный доступ по GET и перенаправлять на страницу логина")
    void shouldDenyUnauthorizedGetAccess(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/v1/book", "/api/v1/comment"})
    @DisplayName("запрещать неаутентифицированный доступ по POST и перенаправлять на страницу логина")
    void shouldDenyUnauthorizedPostAccess(String url) throws Exception {
        mockMvc.perform(post(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/comments"})
    @DisplayName("запрещать неаутентифицированный доступ по PUT и перенаправлять на страницу логина")
    void shouldDenyUnauthorizedPutAccess(String url) throws Exception {
        mockMvc.perform(put(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/books", "/api/comments"})
    @DisplayName("запрещать неаутентифицированный доступ по DELETE и перенаправлять на страницу логина")
    void shouldDenyUnauthorizedDeleteAccess(String url) throws Exception {
        mockMvc.perform(delete(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    @DisplayName("запрещать удаление книги пользователю без роли USER")
    @WithMockUser(roles = "MANAGER")
    void shouldDenyDeleteBookForUsers() throws Exception {
        mockMvc.perform(delete("/api/v1/book/{book}", BOOK))
                .andExpect(status().isForbidden());
    }
}
