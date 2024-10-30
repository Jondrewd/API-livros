package com.apilivros.Dto.Mappers;

import java.util.stream.Collectors;

import com.apilivros.Domain.Author;
import com.apilivros.Dto.AuthorDTO;


public class AuthorMapper {
       public static Author fromDTO(AuthorDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        author.setDescription(dto.getDescription());
        author.setNationality(dto.getNationality());
        author.setBooks(dto.getBooks()
                            .stream()
                            .map(BookMapper::fromDTO)
                            .collect(Collectors.toList()));
        return author;
    }


}
