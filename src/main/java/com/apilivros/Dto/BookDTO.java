package com.apilivros.Dto;

import java.util.ArrayList;
import java.util.List;

import com.apilivros.Domain.Books;
import com.apilivros.Domain.enums.Genre;
import com.apilivros.Dto.Mappers.ReviewMapper;

import jakarta.persistence.Id;

public class BookDTO {
    @Id
    private Integer id;
    private String title;
    private String author;
    private Double rating;
    private String imageUrl;
    private String description;

    private List<Genre> genre = new ArrayList<>();
    private List<ReviewDTO> reviews = new ArrayList<>();
    
    public BookDTO(){}
    public BookDTO(Books book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.genre = book.getGenres();
        this.author = (book.getAuthor() != null) ? book.getAuthor().getName() : "Author not available";
        this.rating = book.getRating();
        this.imageUrl = book.getImageUrl();
        this.reviews = ReviewMapper.convertReviewsToDTO(book.getReviews());
        this.description = book.getDescription();
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
    public List<Genre> getGenre() {
        return genre;
    }
    public void setGenre(List<Genre> genre) {
        this.genre = genre;
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
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
