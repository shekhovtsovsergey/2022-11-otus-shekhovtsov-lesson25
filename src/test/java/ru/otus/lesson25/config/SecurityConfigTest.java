package ru.otus.lesson25.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.lesson25.dto.BookDto;
import ru.otus.lesson25.service.AuthorService;
import ru.otus.lesson25.service.BookService;
import ru.otus.lesson25.service.CommentService;
import ru.otus.lesson25.service.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
@DisplayName("Контроллеры должны")
public class SecurityConfigTest {

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

    @Test
    @DisplayName("должен перенаправлять на страницу аутентификации для доступа к списку книг")
    void whenGetBookList_thenRedirectToAuthenticationPage() throws Exception {
        mockMvc.perform(get("/api/v1/author"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @DisplayName("должен требовать правильные роли для доступа к ресурсу")
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void getAuthorList_shouldReturn403_whenNotAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/author"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("должен требовать правильные роли для доступа к ресурсу")
    @WithMockUser(username = "user", roles = {"USER"})
    void getAuthorList_shouldReturn4200_whenAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("должен требовать аутентификацию для создания книги")
    public void createBook_ShouldRequireAuthentication() throws Exception {
        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Book1\",\"author\": 1,\"genre\": 1}"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @DisplayName("должен требовать аутентификацию для обновления книги")
    public void updateBook_ShouldRequireAuthentication() throws Exception {
        BookDto expectedBook = BookDto.builder().id(1L).name("Book1").build();
        mockMvc.perform(put("/api/v1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(expectedBook)))
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @DisplayName("должен разрешать доступ к списку книг для пользователя с ролью USER")
    void whenGetBookListWithUserRole_thenReturnBookList() throws Exception {
        List<BookDto> bookDtoList = Arrays.asList(new BookDto(), new BookDto());
        given(bookService.getAllBooks()).willReturn(bookDtoList);
        mockMvc.perform(get("/api/v1/book").with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        verify(bookService).getAllBooks();
    }


    @Test
    @DisplayName("должен запрещать доступ к списку книг для пользователя без роли USER")
    void whenGetBookListWithoutUserRole_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/book").with(user("user").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("должен требовать правильные роли для доступа к ресурсу")
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void getBookList_shouldReturn403_whenNotAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("должен требовать правильные роли для доступа к ресурсу")
    @WithMockUser(username = "user", roles = {"USER"})
    void getBooktList_shouldReturn4200_whenAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk());
    }


    private String toJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    @DisplayName("должен требовать авторизацию для доступа к ресурсу")
    void getCommentList_shouldReturn401_whenNotAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/book/1/comment"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @DisplayName("должен требовать правильные роли для доступа к ресурсу")
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void getCommentList_shouldReturn403_whenNotAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/book/1/comment"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("должен требовать правильные роли для доступа к ресурсу")
    @WithMockUser(username = "user", roles = {"USER"})
    void getCommentList_shouldReturn4200_whenAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/book/1/comment"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("должен перенаправлять на страницу аутентификации для доступа к списку жанров")
    void whenGetGenreList_thenRedirectToAuthenticationPage() throws Exception {
        mockMvc.perform(get("/api/v1/genre"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @DisplayName("должен требовать правильные роли для доступа к ресурсу")
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void getGenreList_shouldReturn403_whenNotAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/genre"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("должен требовать правильные роли для доступа к ресурсу")
    @WithMockUser(username = "user", roles = {"USER"})
    void getGenreList_shouldReturn4200_whenAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/genre"))
                .andExpect(status().isOk());
    }



}
