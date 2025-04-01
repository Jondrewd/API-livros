package com.apilivros.Dto;

import java.util.ArrayList;
import java.util.List;

import com.apilivros.Domain.User;
import com.apilivros.Dto.Mappers.ReviewMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {
    @JsonIgnore
    private Long id;
    private String username;
    private String fullName;
    private List<String> roles;

    private List<ReviewDTO> reviews = new ArrayList<>();;

    public UserDTO(){}
    
    public UserDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        fullName = user.getFullName();
        this.reviews = ReviewMapper.convertReviewsToDTO(user.getReviews());
        roles = user.getRoleNames();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
    
    
}
