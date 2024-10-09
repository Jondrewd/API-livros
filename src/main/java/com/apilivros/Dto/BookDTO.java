package com.apilivros.Dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.apilivros.Domain.Books;
import com.apilivros.Domain.Review;
import com.apilivros.Domain.enums.Genre;

import jakarta.persistence.Id;

public class BookDTO {
    @Id
    private Integer id;
    private String title;
    private Genre genre;
    private String author;
    private Double rating;

    private List<ReviewDTO> reviews = new ArrayList<>();
    
    public BookDTO(Books book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.genre = book.getGenre();
        this.author = book.getAuthor();
        this.rating = book.getRating();
        this.reviews = convertReviewsToDTO(book.getReviews());
    }

    private List<ReviewDTO> convertReviewsToDTO(List<Review> reviews) {
        if (reviews == null) {
            return new ArrayList<>();
        }
        return reviews
                .stream()
                .map(ReviewDTO::new) 
                .collect(Collectors.toList());
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
    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genre) {
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

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
    
}
