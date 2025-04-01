package com.apilivros.Dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.apilivros.Domain.Profile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProfileDTO {

    private Long id;
    private String username;
    private String urlIcon;
    private String biography;
    private List<FollowerDTO> followers = new ArrayList<>();
    private List<FollowerDTO> following = new ArrayList<>();
    private List<BookDTO> wishList = new ArrayList<>();
    private List<BookDTO> favoriteBooks = new ArrayList<>();  

    @JsonIgnore
    private UserDTO user;
    private List<ReviewDTO> reviews;

    public ProfileDTO() {}

    public ProfileDTO(Profile profile) {
        this.id = profile.getId();
        this.user = new UserDTO(profile.getUser());
        this.username = profile.getUsername();
        this.urlIcon = profile.getUrlIcon();
        this.biography = profile.getBiography();
        this.reviews = user.getReviews();
        this.wishList = profile.getWishList().stream().map(BookDTO::new).collect(Collectors.toList());
        this.favoriteBooks = profile.getFavoriteBooks().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<FollowerDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowerDTO> followers) {
        this.followers = followers;
    }

    public List<FollowerDTO> getFollowing() {
        return following;
    }

    public void setFollowing(List<FollowerDTO> following) {
        this.following = following;
    }

    public List<BookDTO> getWishList() {
        return wishList;
    }

    public void setWishList(List<BookDTO> wishList) {
        this.wishList = wishList;
    }

    public List<BookDTO> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<BookDTO> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    
}
