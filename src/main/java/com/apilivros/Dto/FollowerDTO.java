package com.apilivros.Dto;

public class FollowerDTO {

    private Long id;
    private String username;
    private String urlIcon;

    public FollowerDTO() {}

    public FollowerDTO(ProfileDTO dto) {
        id = dto.getId();
        username = dto.getUsername();
        urlIcon = dto.getUrlIcon();
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

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }

    
}
