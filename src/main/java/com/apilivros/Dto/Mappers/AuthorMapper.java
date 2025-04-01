package com.apilivros.Dto.Mappers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apilivros.Domain.Author;
import com.apilivros.Dto.AuthorDTO;
import com.apilivros.Services.AuthorService;

@Component
public class AuthorMapper {

    @Autowired
    private  AuthorService service;
    
    public Author fromDTO(AuthorDTO dto) {
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


    
    public AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(author);
    }

    public AuthorDTO searchAuthorByName(String authorName){
        return service.findByAuthorname(authorName);
    }

}
