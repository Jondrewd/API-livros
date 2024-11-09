package com.apilivros.Dto.Mappers;

import java.util.stream.Collectors;

import com.apilivros.Domain.Books;
import com.apilivros.Dto.BookDTO;

public class BookMapper {
    public static Books fromDTO(BookDTO dto) {
        Books book = new Books();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setReviews(dto.getReviews()
                            .stream()
                            .map(ReviewMapper::fromDTO)
                            .collect(Collectors.toList()));
        book.setRating(dto.getRating());
        book.setGenres(dto.getGenre());
        book.setImageUrl(dto.getImageUrl());
        book.setAuthor(AuthorMapper.searchAuthorByName(dto.getAuthor()));
        return book;
    }
    public static BookDTO convertBookToDTO(Books book) {
        return new BookDTO(book);
    }
}
