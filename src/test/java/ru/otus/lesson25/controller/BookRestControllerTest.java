package ru.otus.lesson25.controller;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.lesson25.dto.BookDto;
import ru.otus.lesson25.exception.BookNotFoundException;
import ru.otus.lesson25.service.BookService;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookRestController.class)
@DisplayName("Контроллер книг")
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;

    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен уметь создавать книгу")
    public void createBook_ReturnBook() throws Exception {
        BookDto bookDto = new BookDto(1L,"Book1",1L,1L);
        given(bookService.createBook(bookDto)).willReturn(bookDto);
        mockMvc.perform(post("/api/v1/book")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"name\": \"Book1\",\"author\": 1,\"genre\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Book1")));
        verify(bookService).createBook(bookDto);
    }


    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен уметь обновлять книгу")
    public void updateBook_ReturnBook() throws Exception {
        BookDto expectedBook = BookDto.builder().id(1L).name("Book1").build();
        given(bookService.updateBook(expectedBook)).willReturn(expectedBook);
        mockMvc.perform(put("/api/v1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(expectedBook)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Book1"));
        verify(bookService).updateBook(expectedBook);
    }


    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен уметь получать список книг")
    public void getBookList_ReturnBookList() throws Exception {
        List<BookDto> expectedBookList = Arrays.asList(
                BookDto.builder().id(1L).name("Book1").build(),
                BookDto.builder().id(2L).name("Book2").build(),
                BookDto.builder().id(3L).name("Book3").build()
        );

        given(bookService.getAllBooks()).willReturn(expectedBookList);
        mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Book1")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].name", Matchers.is("Book2")))
                .andExpect(jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(jsonPath("$[2].name", Matchers.is("Book3")));
        verify(bookService).getAllBooks();
    }


    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен уметь получать книг по id")
    public void getBookById_ReturnBook() throws Exception {
        BookDto expectedBook = BookDto.builder().id(1L).name("Book1").build();
        given(bookService.getBookById(1L)).willReturn(expectedBook);
        mockMvc.perform(get("/api/v1/book/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Book1"));
        verify(bookService).getBookById(1L);
    }


    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен уметь удалять книгу по id")
    public void deleteBookById_ReturnVoid() throws Exception {
        mockMvc.perform(delete("/api/v1/book/1"))
                .andExpect(status().isOk());
        verify(bookService).deleteBookById(1L);
    }


    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен уметь ловить ошибки и возвращать бэд-реквест")
    public void handleNotFound_ReturnBadRequest() throws Exception {
        given(bookService.getBookById(1L)).willThrow(new BookNotFoundException("Book Not Found, check your request"));
        mockMvc.perform(get("/api/v1/book/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Book Not Found, check your request"));
        verify(bookService).getBookById(1L);
    }


    private String toJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}