package com.apilivros.Dto;

import java.util.ArrayList;
import java.util.List;

import com.apilivros.Domain.Review;
import com.apilivros.Domain.User;

import jakarta.persistence.Id;

public class UserDTO {
    @Id
    private Integer id;
    private String username;
    private String fullName;

    private List<Review> reviews = new ArrayList<>();;

    public UserDTO(){}
    
    public UserDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        fullName = user.getFullName();
        this.reviews = new ArrayList<>();
        for (Review review : user.getReviews()) {
            this.reviews.add(review);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
}
