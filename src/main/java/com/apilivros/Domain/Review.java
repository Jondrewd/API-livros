package com.apilivros.Domain;

import java.io.Serializable;

import com.apilivros.Domain.exceptions.CommonException;
import com.apilivros.Domain.pk.ReviewID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reviews")
public class Review implements Serializable {
    private static final long serialVersionUID =1L;

    @EmbeddedId
    private ReviewID id;
    
    private String comment;
    private Double score;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id", insertable=false, updatable=false)
    private Books book;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    public Review(){}

    public Review(ReviewID id, User user, String comment, Double score, Books book) {
        this.id = new ReviewID(book.getId(), user.getId());
        this.user = user;
        this.comment = comment;
        if (score > 10) {
            throw new CommonException("Error: Digite uma pontuacao entre 0.0 e 10.0.");
        }else{
            this.score = score;
        }
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        if (score > 10) {
            throw new CommonException("Error: Digite uma pontuacao entre 0.0 e 10.0.");
        }else{
        this.score = score;
        }
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public ReviewID getId() {
        return id;
    }

    public void setId(Books book, User user) {
        this.id = new ReviewID(book.getId(), user.getId());;
    }
    public void setId(Integer bookId, Integer userId) {
        this.id = new ReviewID(bookId, userId);;
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
        Review other = (Review) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }   
}
