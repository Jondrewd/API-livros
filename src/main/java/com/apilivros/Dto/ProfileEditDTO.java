package com.apilivros.Dto;

public class ProfileEditDTO {
    private String username;
    private String urlIcon;
    private String biography;

    public ProfileEditDTO() {
    }
    
    public ProfileEditDTO(String username, String urlIcon, String biography) {
        this.username = username;
        this.urlIcon = urlIcon;
        this.biography = biography;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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

    
}
