package com.apilivros.Dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.apilivros.Domain.Author;
import com.apilivros.Domain.Books;

public class AuthorDTO {
 
    private String name;
    private String description;
    private String nationality;

    private List<BookDTO> books;

    public AuthorDTO() {}

    public AuthorDTO(Author author){
        name = author.getName();
        description = author.getDescription();
        nationality = author.getNationality();
        books = convertBooksToDTO(author.getBooks());
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public List<BookDTO> getBooks() {
        return books;
    }
    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    protected List<BookDTO> convertBooksToDTO(List<Books> books) {
        if (books == null) {
            return new ArrayList<>();
        }
        return books
                .stream()
                .map(BookDTO::new) 
                .collect(Collectors.toList());
        }

}
