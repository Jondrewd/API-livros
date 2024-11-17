package com.apilivros.Dto;


import com.apilivros.Domain.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class ReviewDTO {
    private String comment;
    private Double score;
    private String user;
    private String book;
    
    @JsonIgnore
    private Integer userId;
    @JsonIgnore
    private Integer bookId;

    public ReviewDTO(){}
    public ReviewDTO(Review review) {
        comment = review.getComment();
        score = review.getScore();
        user = review.getUser().getUsername();
        book = review.getBook().getTitle();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
