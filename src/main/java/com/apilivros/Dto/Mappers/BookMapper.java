package com.apilivros.Dto.Mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.apilivros.Domain.Books;
import com.apilivros.Domain.enums.Genre;
import com.apilivros.Dto.BookDTO;

@Component
public class BookMapper {

    public static Books fromDTO(BookDTO dto) {
        Books book = new Books();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setRating(dto.getRating());
        book.setImageUrl(dto.getImageUrl());
        book.setDescription(dto.getDescription());

        book.setGenres(dto.getGenre()
                          .stream()
                          .map(genreName -> Genre.fromName(genreName))  
                          .collect(Collectors.toList()));

        book.setReviews(dto.getReviews()
                            .stream()
                            .map(ReviewMapper::fromDTO)
                            .collect(Collectors.toList()));

        return book;
    }
    
    public static BookDTO convertBookToDTO(Books book) {
        return new BookDTO(book);
    }
}
