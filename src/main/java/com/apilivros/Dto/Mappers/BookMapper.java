package com.apilivros.Dto.Mappers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apilivros.Domain.Books;
import com.apilivros.Dto.AuthorDTO;
import com.apilivros.Dto.BookDTO;

@Component
public class BookMapper {

    @Autowired
    private static AuthorMapper authorMapper;

    public static Books fromDTO(BookDTO dto) {
        Books book = new Books();
        AuthorDTO author = authorMapper.searchAuthorByName(dto.getAuthor());
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setReviews(dto.getReviews()
                            .stream()
                            .map(ReviewMapper::fromDTO)
                            .collect(Collectors.toList()));
        book.setRating(dto.getRating());
        book.setGenres(dto.getGenre());
        book.setImageUrl(dto.getImageUrl());
        book.setAuthor(authorMapper.fromDTO(author));
        return book;
    }
    
    public static BookDTO convertBookToDTO(Books book) {
        return new BookDTO(book);
    }
}
