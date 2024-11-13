package com.apilivros.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.apilivros.Domain.enums.Genre;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Books")
public class Books implements Serializable {
    private static final long serialVersionUID =1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    private Double rating;
    private String imageUrl;
    private String description;

    @OneToMany(mappedBy = "book",  cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    private List<Integer> genres = new ArrayList<>();
    public Books() {
    }

    public Books(Integer id, String title, List<Integer> genres, Author author, Double rating, String imageUrl, String description) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.author = author;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.description = description;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Genre> getGenres() {
        return genres.stream()
                     .map(Genre::valueOf)
                     .collect(Collectors.toList());
    }

    public void setGenres(List<Genre> genres) {
        if (genres != null) {
            this.genres = genres.stream()
                                .map(Genre::getCode)
                                .collect(Collectors.toList());
        }
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getRating() {
        return gerarMedia();
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double gerarMedia() {
        if (reviews.isEmpty()) {
            return rating; 
        }
        double totalRating = 0.0;
        for (Review x : reviews) {
            totalRating += x.getScore();
        }
        return totalRating / reviews.size(); 
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
        Books other = (Books) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
