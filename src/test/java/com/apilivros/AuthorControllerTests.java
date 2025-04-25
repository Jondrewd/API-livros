package com.apilivros;

import com.apilivros.Dto.AuthorDTO;
import com.apilivros.Dto.BookDTO;
import com.apilivros.Dto.Mappers.AuthorMapper;
import com.apilivros.Adapters.AuthorRestController;
import com.apilivros.Config.TestSecurityConfig;
import com.apilivros.Domain.Author;
import com.apilivros.Domain.Books;
import com.apilivros.Domain.Review;
import com.apilivros.Domain.enums.Genre;
import com.apilivros.Services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorRestController.class)
@Import(TestSecurityConfig.class)
public class AuthorControllerTests {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorMapper authorMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

   @Test
    public void testInsertAuthor() throws Exception {

    Author author = new Author(1L, "J.K. Rowling", "Descrição sobre a autora", "Britânica", Arrays.asList());
    AuthorDTO authorDTO = new AuthorDTO(author);

    when(authorMapper.fromDTO(any(AuthorDTO.class))).thenReturn(author);
    when(authorService.insert(any(AuthorDTO.class))).thenReturn(authorDTO);

    mockMvc.perform(post("/author")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorDTO)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "http://localhost/author/1"))
            .andExpect(jsonPath("$.name").value("J.K. Rowling"))
            .andExpect(jsonPath("$.description").value("Descrição sobre a autora"))
            .andExpect(jsonPath("$.nationality").value("Britânica"));
}



    @Test
    void testFindAuthorById() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("J.K. Rowling");
        author.setDescription("Author of Harry Potter");
        author.setNationality("British");

        when(authorService.findById(1L)).thenReturn(new AuthorDTO(author));

        mockMvc.perform(MockMvcRequestBuilders.get("/author/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("J.K. Rowling"))
                .andExpect(jsonPath("$.nationality").value("British"))
                .andExpect(jsonPath("$.description").value("Author of Harry Potter"));
    }

    @Test
    void testUpdateAuthor() throws Exception {
    
        List<Review> reviews = Collections.emptyList();
    
        Books book1 = new Books();
        book1.setId(1L);
        book1.setTitle("A Game of Thrones");
        book1.setAuthor(new Author(1L, "George R.R. Martin", null, null, null));
        book1.setGenres(Arrays.asList(Genre.FANTASY));
        book1.setRating(4.5);
        book1.setImageUrl("http://example.com/image1.jpg");
        book1.setReviews(reviews);
        book1.setDescription("A Game of Thrones - Book 1");
    
        Books book2 = new Books();
        book2.setId(2L);
        book2.setTitle("A Clash of Kings");
        book2.setAuthor(null); 
        book2.setGenres(Arrays.asList(Genre.FANTASY));
        book2.setRating(4.7);
        book2.setImageUrl("http://example.com/image2.jpg");
        book2.setReviews(reviews);
        book2.setDescription("A Clash of Kings - Book 2");
    
        List<Books> books = Arrays.asList(book1, book2);
    
        BookDTO bookDTO1 = new BookDTO(book1);
        BookDTO bookDTO2 = new BookDTO(book2);
    
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("George R.R. Martin");
        authorDTO.setDescription("Author of A Song of Ice and Fire");
        authorDTO.setNationality("American");
        authorDTO.setBooks(Arrays.asList(bookDTO1, bookDTO2));
    
        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        updatedAuthor.setName("George R.R. Martin");
        updatedAuthor.setDescription("Author of A Song of Ice and Fire");
        updatedAuthor.setNationality("American");
        updatedAuthor.setBooks(books);
    
        when(authorMapper.fromDTO(any(AuthorDTO.class))).thenReturn(updatedAuthor);
        when(authorService.update(eq(1L), any(AuthorDTO.class))).thenReturn(authorDTO);
    
        String authorJson = new ObjectMapper().writeValueAsString(authorDTO);
    
        mockMvc.perform(MockMvcRequestBuilders
                .put("/author/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("George R.R. Martin"))
                .andExpect(jsonPath("$.nationality").value("American"))
                .andExpect(jsonPath("$.description").value("Author of A Song of Ice and Fire"))
                .andExpect(jsonPath("$.books[0].title").value("A Game of Thrones"))
                .andExpect(jsonPath("$.books[0].author").value("George R.R. Martin"))
                .andExpect(jsonPath("$.books[1].title").value("A Clash of Kings"))
                .andExpect(jsonPath("$.books[1].author").value("Author not available"));
    }
    
    @Test
    void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/author/1"))
                .andExpect(status().isNoContent());

        verify(authorService).delete(1L);
    }
}
