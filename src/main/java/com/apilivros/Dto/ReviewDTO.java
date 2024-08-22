package com.apilivros.Dto;


import com.apilivros.Domain.Review;

import jakarta.persistence.Id;

public class ReviewDTO {
    @Id
    private Integer id;
    private String comment;
    private Double score;
    private String user;

    public ReviewDTO(Review review) {
        comment = review.getComment();
        score = review.getScore();
        user = review.getUser().getUsername();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    

}
