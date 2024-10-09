package com.apilivros.Dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.apilivros.Domain.Review;
import com.apilivros.Domain.User;

import jakarta.persistence.Id;

public class UserDTO {
    @Id
    private Integer id;
    private String username;
    private String fullName;

    private List<ReviewDTO> reviews = new ArrayList<>();;

    public UserDTO(){}
    
    public UserDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        fullName = user.getFullName();
        this.reviews = convertReviewsToDTO(user.getReviews());
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

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
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
    
}
