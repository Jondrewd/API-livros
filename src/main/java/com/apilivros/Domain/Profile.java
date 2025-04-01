package com.apilivros.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String username;
    private String urlIcon;
    private String biography;

    private List<Long> followersIds = new ArrayList<>();
    private List<Long> followingIds = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_wishlist",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Books> wishList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_favorites",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Books> favoriteBooks = new ArrayList<>();

    public Profile() {}

    public Profile(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.user = user;
    }

    public Profile(Long id, User user, String username, String urlIcon, String biography, List<Long> followersIds,
            List<Long> followingIds, List<Books> wishList, List<Books> favoriteBooks) {
        this.id = id;
        this.user = user;
        this.username = username;
        this.urlIcon = urlIcon;
        this.biography = biography;
        this.followersIds = followersIds;
        this.followingIds = followingIds;
        this.wishList = wishList;
        this.favoriteBooks = favoriteBooks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Long> getFollowersIds() {
        return followersIds;
    }

    public void setFollowersIds(List<Long> followersIds) {
        this.followersIds = followersIds;
    }

    public List<Long> getFollowingIds() {
        return followingIds;
    }

    public void setFollowingIds(List<Long> followingIds) {
        this.followingIds = followingIds;
    }

    public List<Books> getWishList() {
        return wishList;
    }

    public void setWishList(List<Books> wishList) {
        this.wishList = wishList;
    }

    public List<Books> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<Books> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public void addFollower(Long followerId) {
        if (this.id.equals(followerId)) {
            throw new IllegalArgumentException("Não pode seguir a si mesmo.");
        }
        if (followersIds.contains(followerId)) {
            throw new IllegalStateException("Já está seguindo este perfil.");
        }
        followersIds.add(followerId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Profile other = (Profile) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
