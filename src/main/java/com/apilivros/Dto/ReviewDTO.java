package com.apilivros.Dto;

import com.apilivros.Domain.Review;

public class ReviewDTO {
    private String comment;
    private Double score;
    private String profile;
    private String book;

    private Long profileId;
    private Long bookId;

    public ReviewDTO(){}
    public ReviewDTO(Review review) {
        comment = review.getComment();
        score = review.getScore();
        profile = review.getProfile().getUsername();
        book = review.getBook().getTitle();
        profileId = review.getProfile().getId();
        bookId = review.getBook().getId();
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
