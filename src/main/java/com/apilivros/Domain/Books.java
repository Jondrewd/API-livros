package com.apilivros.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.apilivros.Domain.enums.Genre;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Integer genre;
    private String author;
    private Double rating;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    
    public Books() {
    }

    public Books(Integer id, String title, Genre genre, String author, Double rating) {
        this.id = id;
        this.title = title;
        setGenre(genre);
        this.author = author;
        this.rating = rating;
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

    public Genre getGenre() {
        return Genre.valueOf(genre);
    }

    public void setGenre(Genre genre) {
        if (genre != null){
            this.genre = genre.getCode();
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
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


    public Double gerarMedia(){
        rating = 0.0;
        for (Review x : reviews ) {
            rating += x.getScore();
        }
        rating = rating/reviews.toArray().length;
        return rating;
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
