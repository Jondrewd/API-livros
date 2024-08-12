package com.apilivros.Dto;

import com.apilivros.apilivros.Domain.Books;
import jakarta.persistence.Id;

public class BookDTO {
    @Id
    private Integer id;
    private String title;
    private String gender;
    private String author;
    private Double rating;

    
    public BookDTO(Books book) {
        id = book.getId();
        title = book.getTitle();
        gender = book.getGender();
        author = book.getAuthor();
        rating = book.getRating();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }

    
}
