package com.apilivros;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.apilivros.Adapters.BooksRestController;
import com.apilivros.Config.TestSecurityConfig;
import com.apilivros.Domain.Books;
import com.apilivros.Domain.Author;
import com.apilivros.Dto.BookDTO;
import com.apilivros.Services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BooksRestController.class)
@Import(TestSecurityConfig.class)
public class BooksRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @InjectMocks
    private BooksRestController controller;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO bookDTO;
    private Books book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<Integer> genres = List.of(1, 2);
        Author author = new Author(1L, "Author 1", "Description 1", "Nationality 1", null);
        book = new Books(1L, "Test Book", genres, author, 4.5, "imageUrl", "Description 1");
        bookDTO = new BookDTO(book);
    }

    @Test
    void findAll_ReturnsPagedBooks() throws Exception {
        Page<BookDTO> pagedBooks = new PageImpl<>(List.of(bookDTO));
        when(service.findAll(any())).thenReturn(pagedBooks);

        mockMvc.perform(get("/books")
                .param("page", "0")
                .param("size", "1")
                .param("direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Book"));
    }

    @Test
    void findById_ReturnsBook() throws Exception {
        when(service.findById(1L)).thenReturn(bookDTO);

        mockMvc.perform(get("/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void insertBook_ReturnsCreated() throws Exception {
        when(service.insertBook(any(BookDTO.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void deleteBook_ReturnsNoContent() throws Exception {
        doNothing().when(service).deleteBook(1L);
        mockMvc.perform(delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateBook_ReturnsUpdatedBook() throws Exception {
        when(service.updateBook(eq(1L), any(BookDTO.class))).thenReturn(bookDTO);

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }
}
