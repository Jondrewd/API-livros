package com.apilivros.Dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.apilivros.Domain.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProfileDTO {

    private Integer id;
    private String profileName;
    private String urlIcon;
    private String biography;
    private List<UserDTO> followers = new ArrayList<>();
    private List<UserDTO> following = new ArrayList<>();
    private List<BookDTO> wishList = new ArrayList<>();
    private List<BookDTO> favoriteBooks = new ArrayList<>();  

    @JsonIgnore
    private UserDTO user;
    private String username;
    private List<ReviewDTO> reviews;

    public ProfileDTO(){}

    public ProfileDTO(Profile profile){
        id = profile.getId();
        user = new UserDTO(profile.getUser());
        username = user.getUsername();
        profileName = profile.getProfileName();
        urlIcon = profile.getUrlIcon();
        biography = profile.getBiography();
        reviews = user.getReviews();
        followers = profile.getFollowers().stream().map(UserDTO::new).collect(Collectors.toList());
        following = profile.getFollowing().stream().map(UserDTO::new).collect(Collectors.toList());
        wishList = profile.getWishList().stream().map(BookDTO::new).collect(Collectors.toList());
        favoriteBooks = profile.getFavoriteBooks().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Integer id) {
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

    public List<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserDTO> followers) {
        this.followers = followers;
    }

    public List<UserDTO> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserDTO> following) {
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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
    
}
